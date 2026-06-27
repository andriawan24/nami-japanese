package com.namijapanese.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namijapanese.core.database.entity.UserProgressEntity

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE character_id = :characterId")
    suspend fun getProgress(characterId: String): UserProgressEntity?
    
    @Query("SELECT * FROM user_progress WHERE is_learned = 1")
    suspend fun getAllLearned(): List<UserProgressEntity>

    @Query("SELECT * FROM user_progress")
    suspend fun getAllProgress(): List<UserProgressEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: UserProgressEntity)
    
    @Query("SELECT COUNT(*) FROM user_progress WHERE is_learned = 1")
    suspend fun getLearnedCount(): Int
    
    @Query("SELECT COUNT(*) FROM user_progress")
    suspend fun getTotalCount(): Int
}
