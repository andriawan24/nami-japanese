package com.namijapanese.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.namijapanese.core.database.entity.PracticeSessionEntity

@Dao
interface PracticeSessionDao {
    @Insert
    suspend fun insert(session: PracticeSessionEntity): Long

    @Query("SELECT * FROM practice_sessions WHERE owner_id = :ownerId AND practiced_at BETWEEN :startMillis AND :endMillis")
    suspend fun getSessionsBetween(ownerId: String, startMillis: Long, endMillis: Long): List<PracticeSessionEntity>

    @Query("UPDATE practice_sessions SET owner_id = :newOwnerId WHERE owner_id = :oldOwnerId")
    suspend fun migrateOwnerId(oldOwnerId: String, newOwnerId: String)
}
