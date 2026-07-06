// app/(tabs)/explore.em — explore screen
import { ThemedText } from "../components/themed-text.em"
import { ThemedView } from "../components/themed-view.em"

component ExploreScreen {
  render {
    <ThemedView className="container">
      <ThemedText type="title">Explore</ThemedText>
      <ThemedText type="body">Discover features and components.</ThemedText>
    </ThemedView>
  }
}

style "./explore.css"
