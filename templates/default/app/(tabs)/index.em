// app/(tabs)/index.em — home screen
import { HelloWave } from "../components/hello-wave.em"
import { ThemedText } from "../components/themed-text.em"
import { ThemedView } from "../components/themed-view.em"

component HomeScreen {
  render {
    <ThemedView className="container">
      <ThemedText type="title">Welcome!</ThemedText>
      <ThemedText type="subtitle">This is your emo app.</ThemedText>
      <HelloWave />
      <Divider />
      <ThemedText type="link">Edit app/(tabs)/index.em to get started.</ThemedText>
    </ThemedView>
  }
}

style "./index.css"
