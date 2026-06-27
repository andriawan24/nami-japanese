# Nami Japanese

Learn Japanese, One Stroke at a Time.

A premium Japanese learning app focused on learning Hiragana, Katakana, quiz practice, progress tracking, and writing practice using a drawing canvas.

## Features

- **Hiragana Learning**: Master all 46 basic Hiragana characters
- **Katakana Learning**: Master all 46 basic Katakana characters
- **Writing Practice**: Draw characters on a canvas to build muscle memory
- **Quiz System**: Test your knowledge with multiple choice questions
- **Progress Tracking**: Track your learning progress and streaks
- **Settings**: Customize your learning experience

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- MVVM Architecture
- Hilt (Dependency Injection)
- Room (Local Database)
- DataStore (Preferences)

## Project Structure

```
NamiJapanese/
├── app/          # Entry point, navigation, DI
├── core/         # Design system, database, models, repositories
├── feature/      # All screens and ViewModels
└── docs/         # Documentation
```

## Documentation

- [Project Overview](docs/PROJECT_OVERVIEW.md)
- [Software Design Document](docs/SDD.md)
- [Agent Instructions](docs/AGENT.md)
- [MVP Roadmap](docs/MVP_ROADMAP.md)
- [Data Model](docs/DATA_MODEL.md)
- [Architecture Audit](docs/ARCHITECTURE_AUDIT.md)
- [Recommended Structure](docs/RECOMMENDED_STRUCTURE.md)
- [MVP Scope Lock](docs/MVP_SCOPE_LOCK.md)

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle
4. Run on an emulator or device

## Architecture

The app uses a simplified Clean Architecture:

- **app/**: Entry point with Hilt setup and navigation
- **core/**: Shared infrastructure (database, models, design system)
- **feature/**: All screens and ViewModels

### Data Strategy

- **Kana characters**: In-memory (92 immutable characters)
- **User progress**: Room database
- **Preferences**: DataStore
