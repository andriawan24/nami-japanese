package com.namijapanese.core.data.repository

import com.namijapanese.core.database.dao.DailyStreakDao
import com.namijapanese.core.database.entity.DailyStreakEntity
import com.namijapanese.core.model.DailyStreak
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StreakRepository @Inject constructor(
    private val streakDao: DailyStreakDao
) {
    suspend fun getStreak(): DailyStreak? = 
        streakDao.getStreak()?.toDomain()
    
    suspend fun updateStreak() {
        val existingStreak = streakDao.getStreak()
        val today = LocalDate.now().toString()
        
        if (existingStreak == null) {
            streakDao.insertOrUpdate(
                DailyStreakEntity(
                    currentStreak = 1,
                    longestStreak = 1,
                    lastPracticeDate = today,
                    totalPracticeDays = 1
                )
            )
        } else {
            val lastDate = LocalDate.parse(existingStreak.lastPracticeDate)
            val todayDate = LocalDate.parse(today)
            val daysBetween = ChronoUnit.DAYS.between(lastDate, todayDate)
            
            val newCurrentStreak = when {
                daysBetween == 0L -> existingStreak.currentStreak
                daysBetween == 1L -> existingStreak.currentStreak + 1
                else -> 1
            }
            
            val newLongestStreak = maxOf(newCurrentStreak, existingStreak.longestStreak)
            
            streakDao.insertOrUpdate(
                existingStreak.copy(
                    currentStreak = newCurrentStreak,
                    longestStreak = newLongestStreak,
                    lastPracticeDate = today,
                    totalPracticeDays = existingStreak.totalPracticeDays + if (daysBetween > 0) 1 else 0
                )
            )
        }
    }
    
    private fun DailyStreakEntity.toDomain() = DailyStreak(
        currentStreak = currentStreak,
        longestStreak = longestStreak,
        lastPracticeDate = lastPracticeDate,
        totalPracticeDays = totalPracticeDays
    )
}
