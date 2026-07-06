// app/(tabs)/index.em — home screen with all UI elements
import { HelloWave } from "../components/hello-wave.em"
import { ThemedText } from "../components/themed-text.em"
import { ThemedView } from "../components/themed-view.em"

component HomeScreen {
  state webViewUrl = "https://expo.dev"
  state sliderValue = "0.5"
  state switchOn = "true"

  render {
    <SafeAreaView className="safeArea">
      <ScrollView className="scrollView">
        <Column className="container">
          <ThemedText type="title">Welcome to emo!</ThemedText>
          <ThemedText type="subtitle">All native UI elements work.</ThemedText>
          <HelloWave />
          <Divider />

          <ThemedText type="title">WebView</ThemedText>
          <WebView source={webViewUrl} className="webView" />
          <Divider />

          <ThemedText type="title">Inputs</ThemedText>
          <Input placeholder="Type here..." className="input" />
          <Divider />

          <ThemedText type="title">Switch & Slider</ThemedText>
          <Row className="row">
            <Switch value={true} />
            <Slider value={0.5} />
          </Row>
          <Divider />

          <ThemedText type="title">Activity Indicator</ThemedText>
          <Row className="row">
            <ActivityIndicator />
            <Progress value={0.7} />
          </Row>
          <Divider />

          <ThemedText type="title">Picker</ThemedText>
          <Picker options={["Option A", "Option B", "Option C"]} />
          <Divider />

          <ThemedText type="title">Card</ThemedText>
          <Card className="card">
            <Text>This is a card with content inside.</Text>
          </Card>
          <Divider />

          <ThemedText type="title">Checkbox & Radio</ThemedText>
          <Row className="row">
            <Checkbox value={true} />
            <Text>Checked</Text>
            <RadioButton value={true} />
            <Text>Selected</Text>
          </Row>
          <Divider />

          <ThemedText type="title">Icons & FAB</ThemedText>
          <Row className="row">
            <Icon name="home" />
            <Icon name="search" />
            <Icon name="settings" />
          </Row>
          <Fab className="fab" />
          <Divider />

          <ThemedText type="link">Edit app/(tabs)/index.em to get started.</ThemedText>
        </Column>
      </ScrollView>
    </SafeAreaView>
  }
}

style "./index.css"
