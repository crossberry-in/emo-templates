// hooks/use-theme-color.em — theme color hook
// Returns a color for the current color scheme.

component UseThemeColor {
  state color = "#FFFFFF"
  render {
    <Text>{color}</Text>
  }
}
