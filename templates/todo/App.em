component App {
  state newItem = ""
  render {
    <Column className="container">
      <Text fontSize={28} fontWeight="bold">Todo List</Text>
      <Row className="inputRow">
        <TextField placeholder="Add a task" />
        <Button onClick={() => newItem = ""}>Add</Button>
      </Row>
      <Divider />
      <Text className="empty">No tasks yet. Add one above!</Text>
    </Column>
  }
}
style "./App.css"
