# Nami Japanese - Data Model

## Overview

This document defines the core data models for Nami Japanese. All models are designed for offline-first local storage using Room database.

## Entity: KanaCharacter

Represents a single Japanese character (Hiragana or Katakana).

### Fields
```kotlin
@Entity(tableName = "kana_characters")
data class KanaCharacterEntity(
    @PrimaryKey
    val id: String,              // Unique identifier (e.g., "hiragana_a", "katakana_ka")
    
    @ColumnInfo(name = "character")
    val character: String,       // The character itself (e.g., "あ", "ア")
    
    @ColumnInfo(name = "romaji")
    val romaji: String,          // Romanized pronunciation (e.g., "a", "ka")
    
    @ColumnInfo(name = "type")
    val type: String,            // HIRAGANA or KATAKANA
    
    @ColumnInfo(name = "kana_group")
    val group: String,           // Group (a, ka, sa, ta, na, ha, ma, ya, ra, wa)
    
    @ColumnInfo(name = "stroke_count")
    val strokeCount: Int,        // Number of strokes to write
    
    @ColumnInfo(name = "example_word")
    val exampleWord: String?,    // Optional example word using this character
    
    @ColumnInfo(name = "sort_order")
    val sortOrder: Int           // Order within the group (1-5)
)
```

### Indexes
- `type`: For filtering by Hiragana/Katakana
- `group`: For filtering by character group
- `sort_order`: For maintaining character order

### Domain Model
```kotlin
data class KanaCharacter(
    val id: String,
    val character: String,
    val romaji: String,
    val type: KanaType,
    val group: KanaGroup,
    val strokeCount: Int,
    val exampleWord: String?,
    val isLearned: Boolean = false,
    val practiceCount: Int = 0
)
```

### Enums
```kotlin
enum class KanaType {
    HIRAGANA,
    KATAKANA
}

enum class KanaGroup(val displayName: String, val romaji: String) {
    A("A", "a"),
    KA("Ka", "ka"),
    SA("Sa", "sa"),
    TA("Ta", "ta"),
    NA("Na", "na"),
    HA("Ha", "ha"),
    MA("Ma", "ma"),
    YA("Ya", "ya"),
    RA("Ra", "ra"),
    WA("Wa", "wa")
}
```

---

## Entity: Lesson

Represents a learning lesson grouping multiple kana characters.

### Fields
```kotlin
@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey
    val id: String,              // Unique identifier (e.g., "hiragana_a_lesson")
    
    @ColumnInfo(name = "title")
    val title: String,           // Display title (e.g., "Hiragana: A Column")
    
    @ColumnInfo(name = "description")
    val description: String,     // Brief description
    
    @ColumnInfo(name = "type")
    val type: String,            // HIRAGANA or KATAKANA
    
    @ColumnInfo(name = "group_name")
    val groupName: String,       // Which kana group (a, ka, sa, etc.)
    
    @ColumnInfo(name = "character_ids")
    val characterIds: String,    // Comma-separated character IDs
    
    @ColumnInfo(name = "difficulty")
    val difficulty: Int,         // 1-5 difficulty level
    
    @ColumnInfo(name = "sort_order")
    val sortOrder: Int           // Lesson order
)
```

### Domain Model
```kotlin
data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val type: KanaType,
    val group: KanaGroup,
    val characters: List<KanaCharacter>,
    val difficulty: Int,
    val isCompleted: Boolean = false
)
```

---

## Entity: Vocabulary

Represents a Japanese vocabulary word.

### Fields
```kotlin
@Entity(tableName = "vocabulary")
data class VocabularyEntity(
    @PrimaryKey
    val id: String,              // Unique identifier
    
    @ColumnInfo(name = "word")
    val word: String,            // Japanese word (e.g., "ありがとう")
    
    @ColumnInfo(name = "romaji")
    val romaji: String,          // Romanized version
    
    @ColumnInfo(name = "meaning")
    val meaning: String,         // English translation
    
    @ColumnInfo(name = "category")
    val category: String,        // Category (greeting, number, color, etc.)
    
    @ColumnInfo(name = "difficulty")
    val difficulty: Int,         // 1-5 difficulty level
    
    @ColumnInfo(name = "audio_url")
    val audioUrl: String?,       // Optional audio file path
    
    @ColumnInfo(name = "example_sentence")
    val exampleSentence: String? // Example usage
)
```

### Domain Model
```kotlin
data class Vocabulary(
    val id: String,
    val word: String,
    val romaji: String,
    val meaning: String,
    val category: String,
    val difficulty: Int,
    val isLearned: Boolean = false,
    val practiceCount: Int = 0
)
```

---

## Entity: QuizQuestion

Represents a quiz question for assessment.

### Fields
```kotlin
@Entity(tableName = "quiz_questions")
data class QuizQuestionEntity(
    @PrimaryKey
    val id: String,              // Unique identifier
    
    @ColumnInfo(name = "question_type")
    val questionType: String,    // MULTIPLE_CHOICE, MATCHING, FILL_BLANK
    
    @ColumnInfo(name = "question_text")
    val questionText: String,    // The question prompt
    
    @ColumnInfo(name = "correct_answer")
    val correctAnswer: String,   // Correct answer
    
    @ColumnInfo(name = "options")
    val options: String,         // JSON array of options
    
    @ColumnInfo(name = "character_id")
    val characterId: String?,    // Associated kana character (if any)
    
    @ColumnInfo(name = "vocabulary_id")
    val vocabularyId: String?,   // Associated vocabulary (if any)
    
    @ColumnInfo(name = "difficulty")
    val difficulty: Int,         // 1-5 difficulty level
    
    @ColumnInfo(name = "explanation")
    val explanation: String?     // Explanation for the answer
)
```

### Domain Model
```kotlin
data class QuizQuestion(
    val id: String,
    val type: QuizType,
    val questionText: String,
    val correctAnswer: String,
    val options: List<String>,
    val characterId: String? = null,
    val vocabularyId: String? = null,
    val difficulty: Int,
    val explanation: String? = null
)

enum class QuizType {
    MULTIPLE_CHOICE,
    MATCHING,
    FILL_BLANK
}
```

---

## Entity: WritingPractice

Represents a writing practice session.

### Fields
```kotlin
@Entity(tableName = "writing_practices")
data class WritingPracticeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,                // Auto-generated ID
    
    @ColumnInfo(name = "character_id")
    val characterId: String,     // Character being practiced
    
    @ColumnInfo(name = "session_id")
    val sessionId: Long,         // Associated learning session
    
    @ColumnInfo(name = "strokes_data")
    val strokesData: String,     // JSON array of stroke paths
    
    @ColumnInfo(name = "stroke_count")
    val strokeCount: Int,        // Number of strokes drawn
    
    @ColumnInfo(name = "practice_duration_ms")
    val practiceDurationMs: Long, // Time spent practicing
    
    @ColumnInfo(name = "completed")
    val completed: Boolean,      // Whether user marked complete
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long          // Timestamp
)
```

### Domain Model
```kotlin
data class WritingPractice(
    val id: Long,
    val characterId: String,
    val sessionId: Long,
    val strokes: List<Stroke>,
    val strokeCount: Int,
    val durationMs: Long,
    val isCompleted: Boolean,
    val createdAt: Long
)

data class Stroke(
    val points: List<Point>,
    val strokeWidth: Float
)

data class Point(
    val x: Float,
    val y: Float,
    val pressure: Float = 1.0f
)
```

---

## Entity: UserProgress

Tracks user progress for each character.

### Fields
```kotlin
@Entity(
    tableName = "user_progress",
    indices = [Index(value = ["character_id"], unique = true)]
)
data class UserProgressEntity(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val characterId: String,     // Associated kana character
    
    @ColumnInfo(name = "is_learned")
    val isLearned: Boolean,      // Whether character is mastered
    
    @ColumnInfo(name = "practice_count")
    val practiceCount: Int,      // Number of times practiced
    
    @ColumnInfo(name = "best_score")
    val bestScore: Int,          // Best quiz/writing score (0-100)
    
    @ColumnInfo(name = "last_practiced_at")
    val lastPracticedAt: Long?,  // Last practice timestamp
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long          // First learned timestamp
)
```

### Domain Model
```kotlin
data class UserProgress(
    val characterId: String,
    val isLearned: Boolean,
    val practiceCount: Int,
    val bestScore: Int,
    val lastPracticedAt: Long?,
    val createdAt: Long
)
```

---

## Entity: DailyStreak

Tracks user's daily practice streak.

### Fields
```kotlin
@Entity(tableName = "daily_streak")
data class DailyStreakEntity(
    @PrimaryKey
    val id: Int,                 // Always 1 (single row)
    
    @ColumnInfo(name = "current_streak")
    val currentStreak: Int,      // Current consecutive days
    
    @ColumnInfo(name = "longest_streak")
    val longestStreak: Int,      // Longest ever streak
    
    @ColumnInfo(name = "last_practice_date")
    val lastPracticeDate: String, // Last practice date (YYYY-MM-DD)
    
    @ColumnInfo(name = "total_practice_days")
    val totalPracticeDays: Int   // Total days practiced
)
```

### Domain Model
```kotlin
data class DailyStreak(
    val currentStreak: Int,
    val longestStreak: Int,
    val lastPracticeDate: String,
    val totalPracticeDays: Int
)
```

---

## Entity: LearningSession

Represents a learning session (reading, writing, or quiz).

### Fields
```kotlin
@Entity(tableName = "learning_sessions")
data class LearningSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,                // Auto-generated ID
    
    @ColumnInfo(name = "start_time")
    val startTime: Long,         // Session start timestamp
    
    @ColumnInfo(name = "end_time")
    val endTime: Long?,          // Session end timestamp
    
    @ColumnInfo(name = "type")
    val type: String,            // READING, WRITING, QUIZ
    
    @ColumnInfo(name = "characters_learned")
    val charactersLearned: Int,  // Number of characters in session
    
    @ColumnInfo(name = "score")
    val score: Int?,             // Quiz score (if applicable)
    
    @ColumnInfo(name = "completed")
    val completed: Boolean       // Whether session was completed
)
```

### Domain Model
```kotlin
data class LearningSession(
    val id: Long,
    val startTime: Long,
    val endTime: Long?,
    val type: SessionType,
    val charactersLearned: Int,
    val score: Int?,
    val isCompleted: Boolean
)

enum class SessionType {
    READING,
    WRITING,
    QUIZ
}
```

---

## Relationships

### KanaCharacter ↔ UserProgress
- One-to-one relationship
- Each character has one progress record
- Progress created when character is first practiced

### Lesson ↔ KanaCharacter
- One-to-many relationship
- Each lesson contains multiple characters
- Characters shared across lessons (by reference)

### LearningSession ↔ WritingPractice
- One-to-many relationship
- Each session can have multiple practice records
- Practice linked by sessionId

### DailyStreak
- Standalone entity (singleton row)
- Updated daily when user practices

---

## Data Access Objects (DAOs)

### KanaDao
```kotlin
@Dao
interface KanaDao {
    @Query("SELECT * FROM kana_characters WHERE type = :type ORDER BY sort_order")
    suspend fun getCharactersByType(type: String): List<KanaCharacterEntity>
    
    @Query("SELECT * FROM kana_characters WHERE type = :type AND kana_group = :group")
    suspend fun getCharactersByGroup(type: String, group: String): List<KanaCharacterEntity>
    
    @Query("SELECT * FROM kana_characters WHERE id = :id")
    suspend fun getCharacterById(id: String): KanaCharacterEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<KanaCharacterEntity>)
    
    @Query("SELECT COUNT(*) FROM kana_characters WHERE type = :type")
    suspend fun getCountByType(type: String): Int
}
```

### UserProgressDao
```kotlin
@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE character_id = :characterId")
    suspend fun getProgress(characterId: String): UserProgressEntity?
    
    @Query("SELECT * FROM user_progress WHERE is_learned = 1")
    suspend fun getAllLearned(): List<UserProgressEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: UserProgressEntity)
    
    @Query("SELECT COUNT(*) FROM user_progress WHERE is_learned = 1")
    suspend fun getLearnedCount(): Int
}
```

### DailyStreakDao
```kotlin
@Dao
interface DailyStreakDao {
    @Query("SELECT * FROM daily_streak WHERE id = 1")
    suspend fun getStreak(): DailyStreakEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(streak: DailyStreakEntity)
}
```

### LearningSessionDao
```kotlin
@Dao
interface LearningSessionDao {
    @Insert
    suspend fun insert(session: LearningSessionEntity): Long
    
    @Update
    suspend fun update(session: LearningSessionEntity)
    
    @Query("SELECT * FROM learning_sessions ORDER BY start_time DESC LIMIT :limit")
    suspend fun getRecentSessions(limit: Int): List<LearningSessionEntity>
}
```

---

## Seed Data

### Hiragana Characters
Total: 46 characters

| Group | Characters | Stroke Count Range |
|-------|------------|-------------------|
| A | あ い う え お | 2-4 |
| KA | か き く け こ | 1-4 |
| SA | さ し す せ そ | 1-4 |
| TA | た ち つ て と | 1-3 |
| NA | な に ぬ ね の | 1-4 |
| HA | は ひ ふ へ ほ | 1-4 |
| MA | ま み む め も | 2-4 |
| YA | や (ゆ) (よ) | 1-3 |
| RA | ら り る れ ろ | 1-3 |
| WA | わ を ん | 1-2 |

### Katakana Characters
Total: 46 characters

| Group | Characters | Stroke Count Range |
|-------|------------|-------------------|
| A | ア イ ウ エ オ | 1-3 |
| KA | カ キ ク ケ コ | 1-3 |
| SA | シ ス セ ソ | 1-3 |
| TA | タ チ ツ テ ト | 1-3 |
| NA | ナ ニ ヌ ネ ノ | 1-3 |
| HA | ハ ヒ フ ヘ ホ | 1-3 |
| MA | マ ミ ム メ モ | 1-3 |
| YA | ヤ (ユ) (ヨ) | 1-2 |
| RA | ラ リ ル レ ロ | 1-2 |
| WA | ワ ヲ ン | 1-2 |

---

## Migration Strategy

### MVP Approach
- Destructive migrations allowed
- Data is seed-based, can be reloaded
- No user data migration needed initially

### Production Approach
- Incremental migrations
- Version numbering
- Backup before migration
- Rollback capability

---

## Data Flow Examples

### Learning a Character
```
1. User views KanaDetailScreen
2. ViewModel loads character from UseCase
3. UseCase calls Repository
4. Repository queries Room
5. Room returns KanaCharacterEntity
6. Repository maps to domain model
7. UseCase returns KanaCharacter
8. ViewModel updates UiState
9. Screen displays character
```

### Practicing Writing
```
1. User opens WritingPracticeScreen
2. ViewModel loads character
3. User draws strokes on canvas
4. User taps "Complete"
5. ViewModel saves WritingPracticeEntity
6. Repository updates UserProgressEntity
7. DailyStreak updated if needed
8. Session logged in LearningSessionEntity
```

### Taking Quiz
```
1. User starts quiz
2. ViewModel loads QuizQuestions
3. User answers questions
4. ViewModel calculates score
5. Quiz results saved
6. UserProgress updated for characters
7. LearningSession logged
8. Results displayed
```
