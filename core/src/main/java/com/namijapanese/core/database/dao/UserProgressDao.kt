package com.namijapanese.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namijapanese.core.database.entity.UserProgressEntity

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE owner_id = :ownerId AND character_id = :characterId")
    suspend fun getProgress(ownerId: String, characterId: String): UserProgressEntity?

    @Query("SELECT * FROM user_progress WHERE owner_id = :ownerId AND is_learned = 1")
    suspend fun getAllLearned(ownerId: String): List<UserProgressEntity>

    @Query("SELECT * FROM user_progress WHERE owner_id = :ownerId")
    suspend fun getAllProgress(ownerId: String): List<UserProgressEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: UserProgressEntity)

    @Query("SELECT COUNT(*) FROM user_progress WHERE owner_id = :ownerId AND is_learned = 1")
    suspend fun getLearnedCount(ownerId: String): Int

    @Query("SELECT COUNT(*) FROM user_progress WHERE owner_id = :ownerId")
    suspend fun getTotalCount(ownerId: String): Int

    @Query("UPDATE user_progress SET owner_id = :newOwnerId WHERE owner_id = :oldOwnerId")
    suspend fun migrateOwnerId(oldOwnerId: String, newOwnerId: String)
}
