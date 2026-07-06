// components/ui/icon-symbol.em — icon component
component IconSymbol {
  state name = "home"
  render {
    <Text className="icon">{name}</Text>
  }
}

style "./icon-symbol.css"
