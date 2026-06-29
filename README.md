# Nami Japanese

Learn Japanese, One Stroke at a Time.

Nami Japanese is an Android native app for learning Japanese kana through guided study, writing practice, quizzes, and local progress tracking.

## Overview

Nami Japanese helps you master Hiragana and Katakana with an intuitive learning flow. Practice writing characters on a canvas, test your knowledge with quizzes, and track your progress over time — all stored locally on your device.

## Features

- **Hiragana and Katakana Learning** — Master all 92 basic kana characters
- **Kana Detail Screen** — View character details with stroke order guidance
- **Writing Practice** — Draw characters on a canvas to build muscle memory
- **Quiz Mode** — Test your knowledge with multiple choice questions
- **Score Breakdown** — Track your writing and quiz scores per character
- **Weekly Progress** — Visualize your active learning days each week
- **Daily Goal** — Stay motivated with a daily learning target
- **Continue Learning** — Pick up where you left off with smart recommendations
- **Guest Mode** — Start learning immediately without signing in
- **Google Login** — Sign in with your Google account for personalized experience
- **Local-first Progress** — All data stored on device with Room and DataStore
- **Saved Writing Preview** — Your practice strokes are saved for review

## Screens / Learning Flow

1. **Login** — Sign in with Google or continue as Guest
2. **Home** — Dashboard with progress, streaks, weekly activity, and daily goal
3. **Kana List** — Browse Hiragana or Katakana characters
4. **Kana Detail** — View character info and start writing practice
5. **Writing Practice** — Draw the character on canvas, get scored
6. **Quiz** — Answer questions to test your knowledge
7. **Progress** — View overall learning statistics

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3
- **Architecture**: MVVM with Repository Pattern
- **Dependency Injection**: Hilt
- **Local Database**: Room
- **Preferences**: DataStore
- **Navigation**: Compose Navigation
- **Async**: Coroutines with StateFlow
- **Auth**: Google Identity / Credentials API

## Architecture

The app follows a clean MVVM architecture:

- **app/** — Entry point with Hilt setup and navigation
- **core/** — Shared infrastructure (database, models, repositories, design system)
- **feature/** — Screens and ViewModels organized by feature

### Data Strategy

- **Kana characters** — In-memory data (92 immutable characters)
- **User progress** — Room database for scores and practice history
- **Session/preferences** — DataStore for auth state and settings
- **Drawings** — Saved strokes stored in Room for review

## Getting Started

### Prerequisites

- Android Studio Ladybug or later
- JDK 17
- Android SDK 35

### Build

```bash
# Compile Kotlin sources
./gradlew :app:compileDebugKotlin

# Build debug APK
./gradlew :app:assembleDebug
```

### Run

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or device

## Build Requirements

| Component | Version |
|-----------|---------|
| Android Gradle Plugin | 8.7.3 |
| Kotlin | 2.1.0 |
| JDK | 17 |
| compileSdk | 35 |
| minSdk | 26 |
| targetSdk | 35 |

## Project Structure

```
NamiJapanese/
├── app/                  # Entry point, navigation, Hilt setup
├── core/                 # Design system, database, models, repositories
│   ├── data/             # Repositories and data layer
│   ├── datastore/        # DataStore preferences
│   ├── model/            # Data models
│   └── ui/               # Design system components
├── feature/              # Screens and ViewModels
│   ├── auth/             # Login and authentication
│   ├── home/             # Home dashboard
│   ├── kana/             # Kana list and detail
│   ├── writing/          # Writing practice
│   ├── quiz/             # Quiz mode
│   ├── progress/         # Progress tracking
│   └── settings/         # App settings
├── docs/                 # Internal documentation
└── gradle/               # Version catalog
```

## Privacy / Data

- **Google Login** — Used for sign-in identity only
- **Guest Mode** — Fully local, no account required
- **Progress Storage** — All learning data stored locally on device
- **No Cloud Sync** — Data is not synced to any server
- **No Analytics** — No tracking or analytics by default

## Roadmap

- [x] Kana learning (Hiragana & Katakana)
- [x] Writing practice with canvas
- [x] Quiz mode with score tracking
- [x] Guest Mode
- [x] Google login
- [x] Weekly progress tracking
- [x] Daily goal
- [x] Continue Learning recommendations
- [ ] Review weak kana
- [ ] Cloud sync
- [ ] More quiz modes
- [ ] JLPT-style learning path

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on how to contribute.

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
