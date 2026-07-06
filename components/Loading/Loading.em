// Loading — a centered loading indicator with text.
component Loading {
  state message = "Loading…"
  render {
    <Column className="loading">
      <Text className="loadingText">{message}</Text>
    </Column>
  }
}
style "./Loading.css"
