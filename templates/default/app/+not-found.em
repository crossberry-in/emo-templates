// app/+not-found.em — 404 screen
import { ThemedText } from "../components/themed-text.em"
import { ThemedView } from "../components/themed-view.em"

component NotFoundScreen {
  render {
    <ThemedView className="container">
      <ThemedText type="title">404</ThemedText>
      <ThemedText type="body">This screen could not be found.</ThemedText>
    </ThemedView>
  }
}

style "./not-found.css"
