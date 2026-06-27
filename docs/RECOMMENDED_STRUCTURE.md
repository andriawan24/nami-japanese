# Nami Japanese - Recommended Structure

## 4-Module Architecture

```
NamiJapanese/
├── app/
│   ├── build.gradle.kts
│   └── src/main/java/com/namijapanese/app/
│       ├── NamiJapaneseApp.kt              # @HiltAndroidApp
│       ├── MainActivity.kt                 # Navigation graph
│       └── di/
│           └── AppModule.kt                # Hilt @Provides for DB, DataStore
│
├── core/
│   ├── build.gradle.kts
│   └── src/main/java/com/namijapanese/core/
│       ├── model/                          # Domain models
│       │   ├── KanaCharacter.kt
│       │   ├── UserProgress.kt
│       │   ├── DailyStreak.kt
│       │   └── LearningSession.kt
│       ├── data/
│       │   ├── KanaData.kt                 # IN-MEMORY kana characters
│       │   └── repository/
│       │       ├── KanaRepository.kt       # Static data access
│       │       ├── ProgressRepository.kt   # Room-backed progress
│       │       └── StreakRepository.kt     # Room-backed streaks
│       ├── database/
│       │   ├── NamiDatabase.kt
│       │   ├── dao/
│       │   │   ├── UserProgressDao.kt
│       │   │   ├── DailyStreakDao.kt
│       │   │   └── LearningSessionDao.kt
│       │   └── entity/
│       │       ├── UserProgressEntity.kt
│       │       ├── DailyStreakEntity.kt
│       │       └── LearningSessionEntity.kt
│       ├── datastore/
│       │   └── UserPreferencesManager.kt
│       └── designsystem/
│           ├── theme/
│           │   ├── Color.kt
│           │   ├── Theme.kt
│           │   └── Type.kt
│           └── component/
│               ├── NamiButton.kt
│               ├── NamiCard.kt
│               └── KanaCharacterCard.kt
│
├── feature/
│   ├── build.gradle.kts
│   └── src/main/java/com/namijapanese/feature/
│       ├── home/
│       │   ├── HomeScreen.kt
│       │   └── HomeViewModel.kt
│       ├── kana/
│       │   ├── KanaListScreen.kt
│       │   ├── KanaListViewModel.kt
│       │   ├── KanaDetailScreen.kt
│       │   └── KanaDetailViewModel.kt
│       ├── writing/
│       │   ├── WritingPracticeScreen.kt
│       │   └── WritingPracticeViewModel.kt
│       ├── quiz/
│       │   ├── QuizScreen.kt
│       │   └── QuizViewModel.kt
│       ├── progress/
│       │   ├── ProgressScreen.kt
│       │   └── ProgressViewModel.kt
│       └── settings/
│           ├── SettingsScreen.kt
│           └── SettingsViewModel.kt
│
└── docs/
    ├── PROJECT_OVERVIEW.md
    ├── SDD.md
    ├── AGENT.md
    ├── MVP_ROADMAP.md
    ├── DATA_MODEL.md
    ├── ARCHITECTURE_AUDIT.md
    ├── RECOMMENDED_STRUCTURE.md
    └── MVP_SCOPE_LOCK.md
```

## Key Design Decisions

### 1. In-Memory Kana Data

```kotlin
// core/data/KanaData.kt
object KanaData {
    val hiragana = listOf(
        KanaCharacter("h_a", "あ", "a", KanaType.HIRAGANA, KanaGroup.A, 2, null, 1),
        // ... 45 more
    )
    val katakana = listOf(...)
    
    fun getByType(type: KanaType) = if (type == KanaType.HIRAGANA) hiragana else katakana
    fun getById(id: String) = (hiragana + katakana).find { it.id == id }
}
```

### 2. Simplified Repository Pattern

```kotlin
// No interfaces needed for MVP
class KanaRepository @Inject constructor() {
    fun getCharacters(type: KanaType) = KanaData.getByType(type)
    fun getCharacterById(id: String) = KanaData.getById(id)
}

// Room-backed for user data
class ProgressRepository @Inject constructor(
    private val progressDao: UserProgressDao
) {
    suspend fun getProgress(id: String) = progressDao.getProgress(id)?.toDomain()
    suspend fun updateProgress(progress: UserProgress) = progressDao.insertOrUpdate(progress.toEntity())
}
```

### 3. Navigation in App Module

```kotlin
// app/MainActivity.kt
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Hiragana : Screen("hiragana")
    object Katakana : Screen("katakana")
    object KanaDetail : Screen("kana/{characterId}")
    object Writing : Screen("writing/{characterId}")
    object Quiz : Screen("quiz/{quizType}")
    object Progress : Screen("progress")
    object Settings : Screen("settings")
}
```

### 4. Hilt DI in App Module

```kotlin
// app/di/AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = 
        Room.databaseBuilder(context, NamiDatabase::class.java, "nami_japanese_database").build()
    
    @Provides
    fun provideProgressDao(db: NamiDatabase) = db.userProgressDao()
    
    @Provides
    fun provideStreakDao(db: NamiDatabase) = db.dailyStreakDao()
}
```

## Adding New Features

1. Create package in `feature/`
2. Create Screen + ViewModel
3. Add route to `Screen` sealed class in `MainActivity.kt`
4. Add `composable()` to NavHost

## Adding New Core Data

1. Add model to `core/model/`
2. Add entity + DAO to `core/database/`
3. Add repository to `core/data/repository/`
4. Provide DAO in `app/di/AppModule.kt`
