package com.namijapanese.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_streak")
data class DailyStreakEntity(
    @PrimaryKey
    val id: Int = 1,
    
    @ColumnInfo(name = "current_streak")
    val currentStreak: Int,
    
    @ColumnInfo(name = "longest_streak")
    val longestStreak: Int,
    
    @ColumnInfo(name = "last_practice_date")
    val lastPracticeDate: String,
    
    @ColumnInfo(name = "total_practice_days")
    val totalPracticeDays: Int
)
