package com.namijapanese.core.model

enum class KanaStatus {
    NOT_STARTED,
    PRACTICED,
    LEARNED,
    NEEDS_PRACTICE
}

data class KanaCharacter(
    val id: String,
    val character: String,
    val romaji: String,
    val type: KanaType,
    val group: KanaGroup,
    val strokeCount: Int,
    val sortOrder: Int,
    val isLearned: Boolean = false,
    val practiceCount: Int = 0,
    val bestScore: Int = 0,
    val bestWritingScore: Int = 0,
    val bestQuizScore: Int = 0,
    val lastPracticedAt: Long? = null,
    val exampleWord: String? = null,
    val exampleWordReading: String? = null,
    val exampleWordMeaning: String? = null,
    val exampleSentence: String? = null,
    val exampleSentenceReading: String? = null,
    val exampleSentenceMeaning: String? = null
) {
    val hasExample: Boolean
        get() = exampleWord != null && exampleWordMeaning != null

    val totalScore: Int
        get() = (bestWritingScore + bestQuizScore).coerceIn(0, 100)

    val isCompleted: Boolean
        get() = totalScore >= 90

    val status: KanaStatus
        get() = when {
            isCompleted -> KanaStatus.LEARNED
            practiceCount > 0 && bestScore < 80 -> KanaStatus.NEEDS_PRACTICE
            practiceCount > 0 -> KanaStatus.PRACTICED
            else -> KanaStatus.NOT_STARTED
        }
}

enum class KanaType(val displayName: String) {
    HIRAGANA("Hiragana"),
    KATAKANA("Katakana")
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
