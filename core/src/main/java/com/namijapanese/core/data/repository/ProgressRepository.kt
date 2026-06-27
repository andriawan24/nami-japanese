package com.namijapanese.core.data.repository

import com.namijapanese.core.database.dao.PracticeSessionDao
import com.namijapanese.core.database.dao.UserProgressDao
import com.namijapanese.core.database.entity.PracticeSessionEntity
import com.namijapanese.core.database.entity.UserProgressEntity
import com.namijapanese.core.model.UserProgress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressRepository @Inject constructor(
    private val progressDao: UserProgressDao,
    private val practiceSessionDao: PracticeSessionDao
) {
    suspend fun getProgress(characterId: String): UserProgress? = 
        progressDao.getProgress(characterId)?.toDomain()
    
    suspend fun getAllLearned(): List<UserProgress> = 
        progressDao.getAllLearned().map { it.toDomain() }

    suspend fun getAllProgress(): List<UserProgress> =
        progressDao.getAllProgress().map { it.toDomain() }
    
    suspend fun updateProgress(progress: UserProgress) {
        val existing = progressDao.getProgress(progress.characterId)
        
        val entity = if (existing != null) {
            UserProgressEntity(
                characterId = progress.characterId,
                isLearned = progress.isLearned || existing.isLearned,
                practiceCount = existing.practiceCount + 1,
                bestScore = maxOf(existing.bestScore, progress.bestScore),
                bestWritingScore = maxOf(existing.bestWritingScore, progress.bestWritingScore),
                bestQuizScore = maxOf(existing.bestQuizScore, progress.bestQuizScore),
                lastPracticedAt = progress.lastPracticedAt,
                createdAt = existing.createdAt
            )
        } else {
            progress.toEntity()
        }
        
        progressDao.insertOrUpdate(entity)
    }

    suspend fun updateWritingScore(characterId: String, writingScorePercent: Int) {
        val writingScorePart = (writingScorePercent * 0.5).toInt().coerceIn(0, 50)
        val existing = progressDao.getProgress(characterId)
        val now = System.currentTimeMillis()

        val entity = if (existing != null) {
            val newBestWriting = maxOf(existing.bestWritingScore, writingScorePart)
            val totalScore = (newBestWriting + existing.bestQuizScore).coerceIn(0, 100)
            existing.copy(
                bestWritingScore = newBestWriting,
                bestScore = maxOf(existing.bestScore, writingScorePercent),
                isLearned = totalScore >= 90,
                lastPracticedAt = now
            )
        } else {
            UserProgressEntity(
                characterId = characterId,
                isLearned = false,
                practiceCount = 1,
                bestScore = writingScorePercent,
                bestWritingScore = writingScorePart,
                bestQuizScore = 0,
                lastPracticedAt = now,
                createdAt = now
            )
        }

        progressDao.insertOrUpdate(entity)
    }

    suspend fun updateQuizScore(characterId: String, quizScorePercent: Int) {
        val quizScorePart = (quizScorePercent * 0.5).toInt().coerceIn(0, 50)
        val existing = progressDao.getProgress(characterId)
        val now = System.currentTimeMillis()

        val entity = if (existing != null) {
            val newBestQuiz = maxOf(existing.bestQuizScore, quizScorePart)
            val totalScore = (existing.bestWritingScore + newBestQuiz).coerceIn(0, 100)
            existing.copy(
                bestQuizScore = newBestQuiz,
                bestScore = maxOf(existing.bestScore, quizScorePercent),
                isLearned = totalScore >= 90,
                lastPracticedAt = now
            )
        } else {
            UserProgressEntity(
                characterId = characterId,
                isLearned = false,
                practiceCount = 0,
                bestScore = quizScorePercent,
                bestWritingScore = 0,
                bestQuizScore = quizScorePart,
                lastPracticedAt = now,
                createdAt = now
            )
        }

        progressDao.insertOrUpdate(entity)
    }
    
    suspend fun getLearnedCount(): Int = 
        progressDao.getLearnedCount()

    suspend fun recordPracticeSession(
        characterId: String,
        score: Int?,
        passed: Boolean
    ) {
        practiceSessionDao.insert(
            PracticeSessionEntity(
                characterId = characterId,
                practicedAt = System.currentTimeMillis(),
                score = score,
                passed = passed
            )
        )
    }

    suspend fun getPracticeSessionsBetween(startMillis: Long, endMillis: Long): List<PracticeSessionEntity> =
        practiceSessionDao.getSessionsBetween(startMillis, endMillis)
    
    private fun UserProgressEntity.toDomain() = UserProgress(
        characterId = characterId,
        isLearned = isLearned,
        practiceCount = practiceCount,
        bestScore = bestScore,
        bestWritingScore = bestWritingScore,
        bestQuizScore = bestQuizScore,
        lastPracticedAt = lastPracticedAt,
        createdAt = createdAt
    )
    
    private fun UserProgress.toEntity() = UserProgressEntity(
        characterId = characterId,
        isLearned = isLearned,
        practiceCount = 1,
        bestScore = bestScore,
        bestWritingScore = bestWritingScore,
        bestQuizScore = bestQuizScore,
        lastPracticedAt = lastPracticedAt,
        createdAt = createdAt
    )
}
