// app/modal.em — modal screen
import { ThemedText } from "../components/themed-text.em"
import { ThemedView } from "../components/themed-view.em"

component ModalScreen {
  render {
    <ThemedView className="container">
      <ThemedText type="title">Modal</ThemedText>
      <ThemedText type="body">This is a modal screen.</ThemedText>
    </ThemedView>
  }
}

style "./modal.css"
