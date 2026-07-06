// EmptyState — shown when a list has no items.
component EmptyState {
  state title = "Nothing here yet"
  state subtitle = "Add something to get started"
  render {
    <Column className="emptyState">
      <Text className="emptyTitle">{title}</Text>
      <Text className="emptySubtitle">{subtitle}</Text>
    </Column>
  }
}
style "./EmptyState.css"
