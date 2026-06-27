package com.namijapanese.core.model

data class DailyStreak(
    val currentStreak: Int,
    val longestStreak: Int,
    val lastPracticeDate: String,
    val totalPracticeDays: Int
)
