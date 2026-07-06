# emo-templates

Templates and installable components for the [emo](https://github.com/crossberry-in/emo) framework.

## Project templates

Create a new emo project from a template:

```bash
emo init myapp --template counter
```

Available templates:

| Name | Description |
|---|---|
| `blank` | Minimal starting point — one screen, one state variable |
| `counter` | Counter app with +/- buttons and reset |
| `todo` | Todo list with input field and add button |
| `navigation` | Multi-screen app with bottom navigation bar |

## Installable components

Install a component into your project's `components/` directory:

```bash
emo install Card
```

Then use it in your `.em` file:

```em
import { Card } from "./components/card/Card.em"

component App {
  render {
    <Card title="My Card">
      <Text>Card content goes here</Text>
    </Card>
  }
}
```

Available components:

| Name | Description |
|---|---|
| `Card` | Card container with title and content |
| `Modal` | Centered overlay dialog |
| `Form` | Form with labeled text fields |
| `List` | Vertical list with dividers |
| `Loading` | Loading indicator with message |
| `EmptyState` | Empty state placeholder |

## Adding your own templates/components

Fork this repo, add a directory under `templates/` or `components/`, and update `templates.json` or `components.json`. Then submit a PR.
