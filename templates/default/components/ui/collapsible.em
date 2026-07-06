// components/ui/collapsible.em — collapsible section
component Collapsible {
  state expanded = "false"
  render {
    <Column className="collapsible">
      <Text className="collapsibleTitle">{expanded}</Text>
      <Column className="collapsibleContent" />
    </Column>
  }
}

style "./collapsible.css"
