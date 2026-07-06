package dev.emo.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.*
import okhttp3.WebSocket
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * emo app — MainActivity
 *
 * This is the per-project Android entry point. It connects to the emo dev
 * server (URL passed via Intent extra "emo_server") and renders the vtree
 * received over WebSocket as native Jetpack Compose UI.
 *
 * For standalone builds (emo build), this file is replaced by the codegen
 * output that directly hosts the compiled emo components.
 */
class MainActivity : ComponentActivity() {

    private val client = EmoAppClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serverUrl = intent?.getStringExtra("emo_server")
        val projectId = intent?.getStringExtra("emo_project") ?: "unknown"

        setContent {
            EmoAppRoot(client, serverUrl, projectId)
        }

        if (serverUrl != null) {
            client.connect(serverUrl, projectId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.disconnect()
    }
}

/**
 * EmoAppClient — WebSocket client for the emo dev server.
 * Identical to the emo Go preview app's EmoClient, but bundled per-project.
 */
class EmoAppClient {
    data class State(
        val connecting: Boolean = false,
        val connected: Boolean = false,
        val tree: JSONObject? = null,
        val error: String? = null,
    )

    private val _state = mutableStateOf(State())
    val state: State get() = _state.value

    private var ws: WebSocket? = null
    private var serverUrl: String? = null
    private var projectId: String = "unknown"

    fun connect(url: String, projectId: String) {
        if (_state.value.connected) return
        this.serverUrl = url
        this.projectId = projectId
        _state.value = _state.value.copy(connecting = true, error = null)

        val httpClient = OkHttpClient.Builder()
            .pingInterval(15, TimeUnit.SECONDS)
            .build()

        val req = Request.Builder().url(url).build()
        httpClient.newWebSocket(req, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                ws = webSocket
                _state.value = _state.value.copy(connecting = false, connected = true, error = null)
                // Send handshake
                val hs = JSONObject().apply {
                    put("kind", "handshake")
                    put("ts", System.currentTimeMillis())
                    val payload = JSONObject().apply {
                        put("client", "emo-app-android")
                        put("device", android.os.Build.MODEL)
                        put("android", "API ${android.os.Build.VERSION.SDK_INT}")
                        put("appVer", "1.0.0")
                    }
                    put("payload", payload)
                }
                webSocket.send(hs.toString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                val msg = JSONObject(text)
                when (msg.optString("kind")) {
                    "vtree" -> {
                        val payload = msg.optJSONObject("payload")
                        val tree = payload?.opt("root") as? JSONObject
                        if (tree != null) {
                            _state.value = _state.value.copy(tree = tree, error = null)
                        }
                    }
                    "error" -> {
                        val payload = msg.optJSONObject("payload")
                        _state.value = _state.value.copy(error = payload?.optString("message"))
                    }
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                _state.value = _state.value.copy(connected = false, tree = null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _state.value = _state.value.copy(connecting = false, connected = false, error = t.message)
                // Auto-reconnect
                Thread {
                    Thread.sleep(2000)
                    if (!_state.value.connected && serverUrl != null) {
                        connect(serverUrl!!, projectId)
                    }
                }.start()
            }
        })
    }

    fun disconnect() {
        ws?.close(1000, "client disconnect")
        ws = null
        _state.value = State()
    }

    fun sendEvent(token: String, event: String, value: Any? = null) {
        val ws = this.ws ?: return
        val msg = JSONObject().apply {
            put("kind", "event")
            put("ts", System.currentTimeMillis())
            val payload = JSONObject().apply {
                put("token", token)
                put("event", event)
                if (value != null) put("value", value)
            }
            put("payload", payload)
        }
        ws.send(msg.toString())
    }
}

/**
 * Root composable — shows connect form, connecting spinner, or rendered tree.
 */
@Composable
fun EmoAppRoot(client: EmoAppClient, initialUrl: String?, projectId: String) {
    var url by remember { mutableStateOf(initialUrl ?: "") }
    val state by remember { derivedStateOf { client.state } }

    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            state.error != null -> ErrorScreen(state.error!!)
            state.tree != null -> RenderTree(state.tree!!, client)
            state.connecting -> ConnectingScreen()
            else -> ConnectForm(url = url, onUrlChange = { url = it }, onConnect = {
                client.connect(url, projectId)
            })
        }
    }
}

@Composable
fun ConnectForm(url: String, onUrlChange: (String) -> Unit, onConnect: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("emo app", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Connect to your emo dev server", color = Color.Gray)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            label = { Text("Dev server URL") },
            placeholder = { Text("ws://192.168.1.10:7575/ws") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onConnect, modifier = Modifier.fillMaxWidth()) {
            Text("Connect")
        }
    }
}

@Composable
fun ConnectingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
            Text("Connecting to dev server…")
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Text("emo error", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB00020))
            Spacer(Modifier.height(16.dp))
            Text(message, color = Color(0xFFB00020))
        }
    }
}

/**
 * RenderTree — renders the vtree JSON as Jetpack Compose.
 * This is the same renderer used by the emo Go preview app.
 */
@Composable
fun RenderTree(root: JSONObject, client: EmoAppClient) {
    Box(modifier = Modifier.fillMaxSize()) {
        RenderElement(root, client)
    }
}

@Composable
fun RenderElement(el: JSONObject, client: EmoAppClient) {
    when (el.optString("kind")) {
        "scaffold" -> ScaffoldRender(el, client)
        "column" -> ColumnRender(el, client)
        "row" -> RowRender(el, client)
        "view" -> BoxRender(el, client)
        "text" -> TextRender(el)
        "button" -> ButtonRender(el, client)
        "textField" -> TextFieldRender(el, client)
        "image" -> Text("[image]", color = Color.Gray)
        "spacer" -> Spacer(Modifier.height(8.dp))
        "divider" -> HorizontalDivider()
        else -> Text("[${el.optString("kind")}]", color = Color.Red)
    }
}

@Composable
fun ScaffoldRender(el: JSONObject, client: EmoAppClient) {
    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            val children = el.optJSONArray("children")
            if (children != null && children.length() > 0) {
                RenderElement(children.getJSONObject(0), client)
            }
        }
    }
}

@Composable
fun ColumnRender(el: JSONObject, client: EmoAppClient) {
    val props = el.optJSONObject("props")
    val spacing = (props?.opt("spacing") as? Number)?.toDouble() ?: 0.0
    val padding = (props?.opt("padding") as? Number)?.toDouble() ?: 0.0
    val bg = props?.optString("background")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (padding > 0) Modifier.padding(padding.dp) else Modifier)
            .then(if (bg?.startsWith("#") == true) Modifier.background(parseColor(bg)) else Modifier)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing.dp),
    ) {
        EachChild(el) { child -> RenderElement(child, client) }
    }
}

@Composable
fun RowRender(el: JSONObject, client: EmoAppClient) {
    val props = el.optJSONObject("props")
    val spacing = (props?.opt("spacing") as? Number)?.toDouble() ?: 0.0
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.dp),
    ) {
        EachChild(el) { child -> RenderElement(child, client) }
    }
}

@Composable
fun BoxRender(el: JSONObject, client: EmoAppClient) {
    Box(modifier = Modifier.fillMaxWidth()) {
        EachChild(el) { child -> RenderElement(child, client) }
    }
}

@Composable
fun TextRender(el: JSONObject) {
    val props = el.optJSONObject("props")
    val size = (props?.opt("fontSize") as? Number)?.toDouble() ?: 14.0
    val weight = props?.optString("fontWeight") ?: "normal"
    val color = props?.optString("color")
    Text(
        text = el.optString("text"),
        fontSize = size.sp,
        fontWeight = if (weight == "bold") FontWeight.Bold else FontWeight.Normal,
        color = if (color?.startsWith("#") == true) parseColor(color) else Color.Unspecified,
    )
}

@Composable
fun ButtonRender(el: JSONObject, client: EmoAppClient) {
    val label = el.optString("text")
    val clickToken = handlerToken(el, "click")
    Button(onClick = {
        if (clickToken != null) client.sendEvent(clickToken, "click")
    }) {
        Text(label)
    }
}

@Composable
fun TextFieldRender(el: JSONObject, client: EmoAppClient) {
    val placeholder = el.optJSONObject("props")?.optString("placeholder") ?: ""
    val changeToken = handlerToken(el, "change")
    var value by remember { mutableStateOf("") }
    TextField(
        value = value,
        onValueChange = { v ->
            value = v
            if (changeToken != null) client.sendEvent(changeToken, "change", v)
        },
        placeholder = { Text(placeholder) },
    )
}

// --- Helpers ---

@Composable
private fun EachChild(el: JSONObject, render: @Composable (JSONObject) -> Unit) {
    val children = el.optJSONArray("children") ?: return
    for (i in 0 until children.length()) {
        render(children.getJSONObject(i))
    }
}

private fun handlerToken(el: JSONObject, event: String): String? {
    val arr = el.optJSONArray("handlers") ?: return null
    for (i in 0 until arr.length()) {
        val h = arr.getJSONObject(i)
        if (h.optString("event") == event) return h.optString("token")
    }
    return null
}

private fun parseColor(hex: String): Color {
    val s = hex.removePrefix("#")
    return try {
        val v = s.toLong(16)
        when (s.length) {
            6 -> Color((0xFF shl 24) or v.toInt())
            8 -> Color(v.toInt())
            else -> Color.Unspecified
        }
    } catch (e: Exception) { Color.Unspecified }
}
