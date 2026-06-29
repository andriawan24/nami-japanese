package com.namijapanese.core.data.repository

import com.namijapanese.core.database.dao.LearningSessionDao
import com.namijapanese.core.database.dao.PracticeSessionDao
import com.namijapanese.core.database.dao.UserProgressDao
import com.namijapanese.core.database.entity.LearningSessionEntity
import com.namijapanese.core.database.entity.PracticeSessionEntity
import com.namijapanese.core.database.entity.UserProgressEntity
import com.namijapanese.core.model.UserProgress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressRepository @Inject constructor(
    private val progressDao: UserProgressDao,
    private val practiceSessionDao: PracticeSessionDao,
    private val learningSessionDao: LearningSessionDao
) {
    suspend fun getProgress(ownerId: String, characterId: String): UserProgress? =
        progressDao.getProgress(ownerId, characterId)?.toDomain()

    suspend fun getAllLearned(ownerId: String): List<UserProgress> =
        progressDao.getAllLearned(ownerId).map { it.toDomain() }

    suspend fun getAllProgress(ownerId: String): List<UserProgress> =
        progressDao.getAllProgress(ownerId).map { it.toDomain() }

    suspend fun updateProgress(ownerId: String, progress: UserProgress) {
        val existing = progressDao.getProgress(ownerId, progress.characterId)

        val entity = if (existing != null) {
            UserProgressEntity(
                ownerId = ownerId,
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
            progress.toEntity(ownerId)
        }

        progressDao.insertOrUpdate(entity)
    }

    suspend fun updateWritingScore(ownerId: String, characterId: String, writingScorePercent: Int) {
        val writingScorePart = (writingScorePercent * 0.5).toInt().coerceIn(0, 50)
        val existing = progressDao.getProgress(ownerId, characterId)
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
                ownerId = ownerId,
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

    suspend fun updateQuizScore(ownerId: String, characterId: String, quizScorePercent: Int) {
        val quizScorePart = (quizScorePercent * 0.5).toInt().coerceIn(0, 50)
        val existing = progressDao.getProgress(ownerId, characterId)
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
                ownerId = ownerId,
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

    suspend fun getLearnedCount(ownerId: String): Int =
        progressDao.getLearnedCount(ownerId)

    suspend fun recordPracticeSession(
        ownerId: String,
        characterId: String,
        score: Int?,
        passed: Boolean
    ) {
        practiceSessionDao.insert(
            PracticeSessionEntity(
                ownerId = ownerId,
                characterId = characterId,
                practicedAt = System.currentTimeMillis(),
                score = score,
                passed = passed
            )
        )
    }

    suspend fun getPracticeSessionsBetween(ownerId: String, startMillis: Long, endMillis: Long): List<PracticeSessionEntity> =
        practiceSessionDao.getSessionsBetween(ownerId, startMillis, endMillis)

    suspend fun getTodayLearningSessionCount(ownerId: String, startOfDayMillis: Long, startOfTomorrowMillis: Long): Int {
        val writingSessions = practiceSessionDao.getSessionsBetween(ownerId, startOfDayMillis, startOfTomorrowMillis).size
        val quizSessions = learningSessionDao.getCompletedSessionsCountBetween(ownerId, startOfDayMillis, startOfTomorrowMillis)
        return writingSessions + quizSessions
    }

    suspend fun recordLearningSession(ownerId: String, type: String, score: Int?, charactersLearned: Int) {
        val now = System.currentTimeMillis()
        learningSessionDao.insert(
            LearningSessionEntity(
                ownerId = ownerId,
                startTime = now,
                endTime = now,
                type = type,
                charactersLearned = charactersLearned,
                score = score,
                completed = true
            )
        )
    }

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

    private fun UserProgress.toEntity(ownerId: String) = UserProgressEntity(
        ownerId = ownerId,
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
