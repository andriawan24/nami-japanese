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

    @Query("SELECT * FROM learning_sessions WHERE owner_id = :ownerId ORDER BY start_time DESC LIMIT :limit")
    suspend fun getRecentSessions(ownerId: String, limit: Int): List<LearningSessionEntity>

    @Query("SELECT COUNT(*) FROM learning_sessions WHERE owner_id = :ownerId AND completed = 1")
    suspend fun getCompletedSessionsCount(ownerId: String): Int

    @Query("SELECT COUNT(*) FROM learning_sessions WHERE owner_id = :ownerId AND completed = 1 AND start_time BETWEEN :startMillis AND :endMillis")
    suspend fun getCompletedSessionsCountBetween(ownerId: String, startMillis: Long, endMillis: Long): Int

    @Query("UPDATE learning_sessions SET owner_id = :newOwnerId WHERE owner_id = :oldOwnerId")
    suspend fun migrateOwnerId(oldOwnerId: String, newOwnerId: String)
}
