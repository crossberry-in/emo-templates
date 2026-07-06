component App {
  state count = 0
  render {
    <Column className="container">
      <Text fontSize={28} fontWeight="bold">Counter</Text>
      <Text fontSize={48} fontWeight="bold">{count}</Text>
      <Row className="buttonRow">
        <Button onClick={() => count = count - 1}>-</Button>
        <Button onClick={() => count = count + 1}>+</Button>
      </Row>
      <Button onClick={() => count = 0}>Reset</Button>
    </Column>
  }
}
style "./App.css"
