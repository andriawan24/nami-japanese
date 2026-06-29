package com.namijapanese.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namijapanese.core.database.entity.DailyStreakEntity

@Dao
interface DailyStreakDao {
    @Query("SELECT * FROM daily_streak WHERE owner_id = :ownerId AND id = 1")
    suspend fun getStreak(ownerId: String): DailyStreakEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(streak: DailyStreakEntity)

    @Query("UPDATE daily_streak SET owner_id = :newOwnerId WHERE owner_id = :oldOwnerId")
    suspend fun migrateOwnerId(oldOwnerId: String, newOwnerId: String)
}
