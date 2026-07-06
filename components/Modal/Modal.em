// Modal — a centered overlay dialog.
// Usage: <Modal visible={true}>Dialog content</Modal>
component Modal {
  state visible = "false"
  render {
    <Column className="modalOverlay">
      <Column className="modalContent" />
    </Column>
  }
}
style "./Modal.css"
