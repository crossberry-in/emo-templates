// hooks/use-color-scheme.em — color scheme hook
// In emo, hooks are Go functions that return reactive state.
// This hook detects light/dark mode (placeholder for now).

component UseColorScheme {
  state scheme = "light"
  render {
    <Text>{scheme}</Text>
  }
}
