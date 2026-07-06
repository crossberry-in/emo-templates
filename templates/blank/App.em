// App.em — blank template
component App {
  state message = "Hello, emo!"
  render {
    <Column className="container">
      <Text fontSize={28} fontWeight="bold">emo app</Text>
      <Text fontSize={16}>{message}</Text>
      <Divider />
      <Text className="hint">Edit App.em and save for live reload!</Text>
    </Column>
  }
}
style "./App.css"
