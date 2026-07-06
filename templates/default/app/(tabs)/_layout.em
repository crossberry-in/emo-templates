// app/(tabs)/_layout.em — bottom tab navigation
// Defines the tabs: Home, Explore, Profile

component TabsLayout {
  state activeTab = "home"
  render {
    <Column className="tabsContainer">
      <Column className="tabContent">
        <Text className="tabTitle">{activeTab}</Text>
        <Text className="tabBody">This is the {activeTab} tab.</Text>
      </Column>
      <Row className="tabBar">
        <Button onClick={() => activeTab = "home"}>Home</Button>
        <Button onClick={() => activeTab = "explore"}>Explore</Button>
        <Button onClick={() => activeTab = "profile"}>Profile</Button>
      </Row>
    </Column>
  }
}

style "./(tabs).css"
