package com.namijapanese.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_progress",
    indices = [
        Index(value = ["is_learned"])
    ]
)
data class UserProgressEntity(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val characterId: String,
    
    @ColumnInfo(name = "is_learned")
    val isLearned: Boolean,
    
    @ColumnInfo(name = "practice_count")
    val practiceCount: Int,
    
    @ColumnInfo(name = "best_score")
    val bestScore: Int,
    
    @ColumnInfo(name = "best_writing_score")
    val bestWritingScore: Int = 0,
    
    @ColumnInfo(name = "best_quiz_score")
    val bestQuizScore: Int = 0,
    
    @ColumnInfo(name = "last_practiced_at")
    val lastPracticedAt: Long?,
    
    @ColumnInfo(name = "created_at")
    val createdAt: Long
)
