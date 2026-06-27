package com.namijapanese.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.namijapanese.core.database.entity.LearningSessionEntity

@Dao
interface LearningSessionDao {
    @Insert
    suspend fun insert(session: LearningSessionEntity): Long
    
    @Update
    suspend fun update(session: LearningSessionEntity)
    
    @Query("SELECT * FROM learning_sessions ORDER BY start_time DESC LIMIT :limit")
    suspend fun getRecentSessions(limit: Int): List<LearningSessionEntity>
    
    @Query("SELECT COUNT(*) FROM learning_sessions WHERE completed = 1")
    suspend fun getCompletedSessionsCount(): Int
}
