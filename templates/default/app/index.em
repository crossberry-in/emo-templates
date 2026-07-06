// app/index.em — WebView demo
// Shows a full-screen web view with a navigation bar.

component App {
  state url = "https://crossberry-in.github.io/emo-docs/"
  state title = "emo WebView"

  render {
    <SafeAreaView className="safe">
      <Column className="container">
        <TopBar title={title} />

        <Row className="navBar">
          <Button onClick={() => url = "https://crossberry-in.github.io/emo-docs/"}>Emo</Button>
          <Button onClick={() => url = "https://github.com"}>GitHub</Button>
          <Button onClick={() => url = "https://google.com"}>Google</Button>
        </Row>

        <WebView source={url} className="webView" />
      </Column>
    </SafeAreaView>
  }
}

style "./index.css"
