# Nami Japanese - Software Design Document

## 1. App Architecture

### Architecture Pattern
**Clean Architecture with MVVM**

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                    │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │   Screen    │  │  ViewModel  │  │   State     │     │
│  │  (Compose)  │←─│  (StateFlow)│←─│  (UiState)  │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
├─────────────────────────────────────────────────────────┤
│                      Domain Layer                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │  Use Cases  │  │ Repository  │  │   Models    │     │
│  │             │←─│ Interfaces  │←─│  (Domain)   │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
├─────────────────────────────────────────────────────────┤
│                       Data Layer                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │ Repository  │  │  Local DS   │  │   Models    │     │
│  │    Impl     │←─│  (Room/DS)  │←─│  (Data)     │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
├─────────────────────────────────────────────────────────┤
│                      Core Layer                         │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐      │
│  │ Design  │ │Navigation│ │ Database│ │DataStore│      │
│  │ System  │ │         │ │         │ │         │      │
│  └─────────┘ └─────────┘ └─────────┘ └─────────┘      │
└─────────────────────────────────────────────────────────┘
```

### Dependency Direction
- Presentation → Domain → Data
- Domain has NO dependency on Presentation or Data
- Data implements Domain repository interfaces
- Core modules are shared across all layers

## 2. Module/Package Structure

```
NamiJapanese/
├── app/                          # Application module
├── core/
│   ├── designsystem/            # Theme, colors, typography, components
│   ├── navigation/              # Navigation graph, routes, arguments
│   ├── database/                # Room database, DAOs, entities
│   ├── datastore/               # DataStore preferences
│   ├── model/                   # Domain models
│   └── common/                  # Extensions, utilities
├── domain/
│   ├── repository/              # Repository interfaces
│   └── usecase/                 # Use cases
├── data/
│   ├── repository/              # Repository implementations
│   └── local/                   # Local data sources, mappers
├── feature/
│   ├── onboarding/              # Onboarding screens
│   ├── home/                    # Home dashboard
│   ├── kana/                    # Hiragana/Katakana lessons
│   ├── writing/                 # Writing practice
│   ├── quiz/                    # Quiz and flashcards
│   ├── progress/                # Progress tracking
│   └── settings/                # Settings
└── docs/                        # Documentation
```

## 3. Data Flow

### Screen State Flow
```
User Action → Composable → ViewModel → UseCase → Repository → DataSource
                                                              ↓
User Action ← Composable ← ViewModel ← UseCase ← Repository ← DataSource
```

### Example: Loading Hiragana Characters
1. HiraganaListScreen enters composition
2. LaunchedEffect triggers ViewModel.loadCharacters()
3. ViewModel calls GetHiraganaCharactersUseCase
4. UseCase calls KanaRepository.getCharacters(HIRAGANA)
5. Repository queries Room database
6. Room returns List<KanaCharacterEntity>
7. Repository maps to domain KanaCharacter models
8. UseCase returns list to ViewModel
9. ViewModel emits new UiState via StateFlow
10. Screen recomposes with character list

## 4. UI Flow

### App Navigation Flow
```
Splash → Onboarding → Home
                      ├── Hiragana Lessons → Lesson Detail → Kana Detail
                      │                                        ├── Writing Practice
                      │                                        └── Quiz
                      ├── Katakana Lessons → Lesson Detail → Kana Detail
                      │                                        ├── Writing Practice
                      │                                        └── Quiz
                      ├── Flashcards → Session
                      ├── Progress → Detail
                      └── Settings
```

### Screen Responsibilities
- **Onboarding**: Welcome, learning goals selection, daily reminder setup
- **Home**: Progress overview, continue learning, quick actions
- **Kana List**: Grid of characters with group filters
- **Kana Detail**: Character info, stroke animation, practice button
- **Writing Practice**: Canvas for drawing, reference display
- **Quiz**: Question display, answer selection, results
- **Progress**: Charts, streaks, achievements
- **Settings**: Preferences, data management, about

## 5. Database Design

### Room Database Schema
```kotlin
@Entity(tableName = "kana_characters")
data class KanaCharacterEntity(
    @PrimaryKey val id: String,
    val character: String,
    val romaji: String,
    val type: String,          // HIRAGANA or KATAKANA
    val group: String,         // a, ka, sa, ta, etc.
    val strokeCount: Int,
    val exampleWord: String?,
    val sortOrder: Int
)

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val characterId: String,
    val isLearned: Boolean,
    val practiceCount: Int,
    val bestScore: Int,
    val lastPracticedAt: Long?,
    val createdAt: Long
)

@Entity(tableName = "daily_streak")
data class DailyStreakEntity(
    @PrimaryKey val id: Int,
    val currentStreak: Int,
    val longestStreak: Int,
    val lastPracticeDate: String,
    val totalPracticeDays: Int
)

@Entity(tableName = "learning_sessions")
data class LearningSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val startTime: Long,
    val endTime: Long?,
    val type: String,          // READING, WRITING, QUIZ
    val charactersLearned: Int,
    val score: Int?
)
```

### Indexes
- `kana_characters`: Index on type and group
- `user_progress`: Index on isLearned
- `learning_sessions`: Index on startTime

## 6. State Management

### StateFlow Pattern
```kotlin
// Screen State
data class HiraganaListUiState(
    val characters: List<KanaCharacter> = emptyList(),
    val selectedGroup: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel
class HiraganaListViewModel @Inject constructor(
    private val getCharacters: GetHiraganaCharactersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HiraganaListUiState())
    val uiState: StateFlow<HiraganaListUiState> = _uiState.asStateFlow()
    
    init {
        loadCharacters()
    }
    
    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getCharacters(HIRAGANA)
                .onSuccess { characters ->
                    _uiState.update { 
                        it.copy(characters = characters, isLoading = false) 
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(error = error.message, isLoading = false) 
                    }
                }
        }
    }
}
```

### State Design Principles
- Single source of truth per screen
- Immutable state objects
- One-way data flow
- Side effects handled by ViewModel
- Loading, Success, Error states for async operations

## 7. Navigation Design

### Type-Safe Navigation
```kotlin
// Routes
sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object HiraganaLessons : Screen("hiragana")
    object KatakanaLessons : Screen("katakana")
    object KanaDetail : Screen("kana/{characterId}") {
        fun createRoute(characterId: String) = "kana/$characterId"
    }
    object WritingPractice : Screen("writing/{characterId}") {
        fun createRoute(characterId: String) = "writing/$characterId"
    }
    object Quiz : Screen("quiz/{type}") {
        fun createRoute(type: String) = "quiz/$type"
    }
    object Flashcards : Screen("flashcards")
    object Progress : Screen("progress")
    object Settings : Screen("settings")
}
```

### Navigation Graph
```kotlin
@Composable
fun NamiNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Onboarding.route) { OnboardingScreen() }
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.HiraganaLessons.route) { HiraganaListScreen() }
        composable(Screen.KatakanaLessons.route) { KatakanaListScreen() }
        composable(Screen.KanaDetail.route) { KanaDetailScreen() }
        composable(Screen.WritingPractice.route) { WritingPracticeScreen() }
        composable(Screen.Flashcards.route) { FlashcardScreen() }
        composable(Screen.Quiz.route) { QuizScreen() }
        composable(Screen.Progress.route) { ProgressScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}
```

## 8. Offline-First Approach

### Strategy
- All kana data is bundled as seed data in the database
- Room database serves as the single source of truth
- DataStore for user preferences only
- No network calls required for MVP
- Future: Add sync capability for user accounts

### Seed Data Loading
```kotlin
class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Load initial kana data on first launch
        CoroutineScope(Dispatchers.IO).launch {
            database.kanaDao().insertAll(KanaData.getAllCharacters())
        }
    }
}
```

### Data Migration Strategy
- Room handles schema migrations
- Version numbers incremented for changes
- Destructive migrations allowed for MVP (data is seed-based)

## 9. Future AI Tutor Integration

### Architecture Extension
```
┌─────────────────────────────────────┐
│          AI Tutor Module            │
│  ┌─────────────┐  ┌─────────────┐  │
│  │   Chat UI   │←─│  AI Service │  │
│  └─────────────┘  └─────────────┘  │
│                      ↕              │
│  ┌─────────────┐  ┌─────────────┐  │
│  │   Prompt    │  │   Context   │  │
│  │  Templates  │  │   Manager   │  │
│  └─────────────┘  └─────────────┘  │
└─────────────────────────────────────┘
```

### Planned Features
- Natural language conversation for grammar explanations
- Context-aware hints during writing practice
- Personalized learning path recommendations
- Pronunciation feedback (future audio integration)
- Cultural context and usage examples

### Integration Points
- Quiz screen: AI-generated explanations for wrong answers
- Writing screen: Real-time feedback on stroke order
- Flashcards: Adaptive difficulty based on performance
- Progress screen: AI-powered study recommendations

## 10. Future Handwriting Recognition Integration

### ML Kit Integration
```
┌─────────────────────────────────────┐
│      Handwriting Recognition        │
│  ┌─────────────┐  ┌─────────────┐  │
│  │   Canvas    │←─│  ML Kit     │  │
│  │   Input     │  │  Text API   │  │
│  └─────────────┘  └─────────────┘  │
│        ↓                            │
│  ┌─────────────┐  ┌─────────────┐  │
│  │   Stroke    │  │   Score     │  │
│  │  Analyzer   │→ │  Calculator │  │
│  └─────────────┘  └─────────────┘  │
└─────────────────────────────────────┘
```

### Planned Capabilities
- Real-time character recognition as user draws
- Stroke order validation and scoring
- Stroke count verification
- Character proportion analysis
- Detailed feedback with improvement suggestions

### Technical Approach
- Use ML Kit's Digital Ink Recognition API
- Train custom model for Japanese characters
- On-device processing for privacy
- Fallback to basic canvas for unsupported devices

### Scoring Algorithm
```kotlin
data class WritingScore(
    val characterMatch: Float,    // 0.0 - 1.0
    val strokeOrder: Float,       // 0.0 - 1.0
    val proportions: Float,       // 0.0 - 1.0
    val overall: Float            // Weighted average
)
```
