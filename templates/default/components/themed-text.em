// components/themed-text.em — themed text component
// Supports type variants: title, subtitle, body, link

component ThemedText {
  state type = "body"
  render {
    <Text className="themedText">
      <Text />
    </Text>
  }
}

style "./themed-text.css"
