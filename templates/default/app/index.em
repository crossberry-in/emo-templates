// app/index.em — entry point (like Expo Router's index route)
// This is the first screen emo start loads. It renders the tabs layout.
import { TabsLayout } from "./(tabs)/_layout.em"

component App {
  render {
    <TabsLayout />
  }
}

style "./index.css"
