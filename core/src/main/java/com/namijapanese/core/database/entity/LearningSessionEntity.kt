package com.namijapanese.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "learning_sessions",
    indices = [
        Index(value = ["owner_id"]),
        Index(value = ["start_time"])
    ]
)
data class LearningSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "owner_id")
    val ownerId: String,

    @ColumnInfo(name = "start_time")
    val startTime: Long,

    @ColumnInfo(name = "end_time")
    val endTime: Long?,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "characters_learned")
    val charactersLearned: Int,

    @ColumnInfo(name = "score")
    val score: Int?,

    @ColumnInfo(name = "completed")
    val completed: Boolean
)
