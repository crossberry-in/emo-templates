// components/external-link.em — external link component
component ExternalLink {
  state url = ""
  render {
    <Text className="link">{url}</Text>
  }
}

style "./external-link.css"
