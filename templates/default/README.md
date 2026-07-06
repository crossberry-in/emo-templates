# my-app

An emo app — built with the emo 0.1 SDK.

## Get started

1. Start the dev server:

   ```bash
   emo start
   ```

2. In another terminal, launch on Android:

   ```bash
   emo go
   ```

3. Open your app in the emo Go preview app (or this project's android/ app).

## Project structure

```
my-app/
├── app/                    # File-based routing (like Expo Router)
│   ├── (tabs)/             # Tab navigation group
│   │   ├── _layout.em      # Tab bar layout
│   │   ├── index.em        # Home screen
│   │   └── explore.em      # Explore screen
│   ├── _layout.em          # Root layout
│   ├── modal.em            # Modal screen
│   └── +not-found.em       # 404 screen
├── components/             # Reusable components
│   ├── themed-text.em      # Themed text (title, body, link variants)
│   ├── themed-view.em      # Themed container
│   ├── hello-wave.em       # Animated wave
│   ├── external-link.em    # External link
│   └── ui/                 # UI primitives
│       ├── collapsible.em  # Collapsible section
│       └── icon-symbol.em  # Icon
├── hooks/                  # Custom hooks
│   ├── use-color-scheme.em
│   └── use-theme-color.em
├── assets/                 # Static assets
│   └── images/             # App icons and images
├── scripts/                # Project scripts
│   └── reset-project.em    # Reset to blank project
├── android/                # Native Android project
│   ├── app/
│   │   ├── build.gradle.kts
│   │   └── src/main/
│   │       ├── AndroidManifest.xml
│   │       └── java/dev/emo/app/
│   │           └── MainActivity.kt   # Connects to emo dev server
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   └── gradle.properties
├── emo.json                # App config (like app.json)
├── emo.toml                # Project config
└── .gitignore
```

## Edit and see changes instantly

Edit any `.em` file and save — the emo dev server pushes the new view tree
to your device instantly. No rebuild, no restart.

## Build a standalone APK

```bash
emo build
```

This generates Kotlin code from your `.em` files and assembles a standalone
APK that doesn't need the dev server.

## Learn more

- [emo framework](https://github.com/crossberry-in/emo)
- [emo templates](https://github.com/crossberry-in/emo-templates)
- [.em language reference](https://github.com/crossberry-in/emo#the-em-language)
