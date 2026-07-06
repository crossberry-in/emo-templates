// Form — a simple form with labeled text fields.
component Form {
  render {
    <Column className="form">
      <Text className="label">Name</Text>
      <TextField placeholder="Enter name" />
      <Text className="label">Email</Text>
      <TextField placeholder="Enter email" />
      <Button>Submit</Button>
    </Column>
  }
}
style "./Form.css"
