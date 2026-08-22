# Material Components Showcase

Android application that acts as an interactive showcase of Material Design 3 components using Jetpack Compose.

## Stack

- Android Gradle Plugin 9.2.0
- Gradle 9.4.1 in CI
- JDK 17
- compileSdk / targetSdk 37
- Kotlin + Compose Compiler 2.3.21
- Compose BOM 2026.08.00
- Material 3 1.4.0

## Component coverage

The catalog includes interactive examples for buttons, icon buttons, floating action buttons, segmented buttons, chips, cards, checkboxes, tri-state checkboxes, radio buttons, switches, sliders, range sliders, text fields, progress indicators, badges, tabs, lists, dividers, top app bars, bottom app bars, navigation bars, dropdown menus, dialogs, modal bottom sheets and snackbars.

The app is structured as a living component reference: examples keep local state and expose actual interaction instead of static screenshots.

## Build

GitHub Actions runs `.github/workflows/build-apk.yml` on pushes and pull requests to `main`, and can also be started manually. The workflow builds:

`app/build/outputs/apk/debug/app-debug.apk`

and uploads it as the artifact:

`material-showcase-debug-apk`

For a local build with Gradle 9.4.1 and JDK 17:

```bash
gradle :app:assembleDebug
```
