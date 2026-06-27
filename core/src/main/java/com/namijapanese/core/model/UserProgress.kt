package com.namijapanese.core.model

data class UserProgress(
    val characterId: String,
    val isLearned: Boolean,
    val practiceCount: Int,
    val bestScore: Int,
    val bestWritingScore: Int = 0,
    val bestQuizScore: Int = 0,
    val lastPracticedAt: Long?,
    val createdAt: Long
) {
    val totalScore: Int
        get() = (bestWritingScore + bestQuizScore).coerceIn(0, 100)

    val isCompleted: Boolean
        get() = totalScore >= 90
}
