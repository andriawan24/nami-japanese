package com.namijapanese.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namijapanese.core.database.entity.DailyStreakEntity

@Dao
interface DailyStreakDao {
    @Query("SELECT * FROM daily_streak WHERE id = 1")
    suspend fun getStreak(): DailyStreakEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(streak: DailyStreakEntity)
}
