package com.namijapanese.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "practice_sessions",
    indices = [
        Index(value = ["practiced_at"])
    ]
)
data class PracticeSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "character_id")
    val characterId: String,

    @ColumnInfo(name = "practiced_at")
    val practicedAt: Long,

    @ColumnInfo(name = "score")
    val score: Int?,

    @ColumnInfo(name = "passed")
    val passed: Boolean
)
