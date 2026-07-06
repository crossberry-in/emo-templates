// Card — a card container with optional title and content.
// Usage: <Card title="My Title">Content here</Card>
component Card {
  state title = ""
  render {
    <Column className="card">
      <Text className="cardTitle">{title}</Text>
      <Column className="cardContent" />
    </Column>
  }
}
style "./Card.css"
