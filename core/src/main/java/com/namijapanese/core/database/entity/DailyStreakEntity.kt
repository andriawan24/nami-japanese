package com.namijapanese.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "daily_streak",
    primaryKeys = ["owner_id", "id"],
    indices = [
        Index(value = ["owner_id"])
    ]
)
data class DailyStreakEntity(
    @ColumnInfo(name = "owner_id")
    val ownerId: String,

    @ColumnInfo(name = "id")
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
