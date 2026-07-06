# AGENTS.md

Guidance for AI agents working on this emo project.

## Architecture

- **`.em` files** — UI components in emo's JSX-like language
- **`.css` files** — styles for components (CSS syntax with dp/sp units)
- **`app/`** — file-based routing ( Expo Router style)
- **`components/`** — reusable components
- **`android/`** — native Android project (Kotlin + Jetpack Compose + Gradle)

## emo dev server

Run `emo start` to start the dev server. It:
1. Watches `.em` and `.css` files for changes
2. Transpiles `.em` → vtree on each save
3. Pushes vtree to connected Android device via WebSocket
4. Renders vtree as native Jetpack Compose UI

## Building APK

Run `emo build` to generate a standalone APK. This:
1. Transpiles `.em` files to Go source
2. Generates Kotlin from the vtree
3. Assembles the APK via Gradle
