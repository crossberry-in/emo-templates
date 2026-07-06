component App {
  state screen = "home"
  render {
    <Column className="container">
      <Text fontSize={24} fontWeight="bold">{screen}</Text>
      <Divider />
      <Column className="content">
        <Text className="body">This is the {screen} screen.</Text>
      </Column>
      <Row className="navBar">
        <Button onClick={() => screen = "home"}>Home</Button>
        <Button onClick={() => screen = "search"}>Search</Button>
        <Button onClick={() => screen = "profile"}>Profile</Button>
      </Row>
    </Column>
  }
}
style "./App.css"
