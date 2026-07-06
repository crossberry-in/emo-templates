// app/_layout.em — root layout component
// This wraps all routes in the app. Like Expo Router's _layout.tsx.

component RootLayout {
  render {
    <Column className="root">
      <Column className="content" />
    </Column>
  }
}

style "./_layout.css"
