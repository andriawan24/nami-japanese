package com.namijapanese.core.data.repository

import com.namijapanese.core.database.dao.DailyStreakDao
import com.namijapanese.core.database.dao.KanaDrawingDao
import com.namijapanese.core.database.dao.LearningSessionDao
import com.namijapanese.core.database.dao.PracticeSessionDao
import com.namijapanese.core.database.dao.UserProgressDao
import com.namijapanese.core.datastore.AuthDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressMigrationHelper @Inject constructor(
    private val progressDao: UserProgressDao,
    private val drawingDao: KanaDrawingDao,
    private val practiceSessionDao: PracticeSessionDao,
    private val learningSessionDao: LearningSessionDao,
    private val streakDao: DailyStreakDao,
    private val authDataStore: AuthDataStore
) {
    suspend fun migrateLegacyProgressIfNeeded() {
        val hasMigrated = authDataStore.hasMigratedLegacyProgress.first()
        if (hasMigrated) return

        val session = authDataStore.userSessionFlow.first()
        val newOwnerId = session.googleUserId ?: session.email
        if (newOwnerId == null || newOwnerId == "local_legacy") return

        val oldOwnerId = "local_legacy"

        progressDao.migrateOwnerId(oldOwnerId, newOwnerId)
        drawingDao.migrateOwnerId(oldOwnerId, newOwnerId)
        practiceSessionDao.migrateOwnerId(oldOwnerId, newOwnerId)
        learningSessionDao.migrateOwnerId(oldOwnerId, newOwnerId)
        streakDao.migrateOwnerId(oldOwnerId, newOwnerId)

        authDataStore.setHasMigratedLegacyProgress(true)
    }
}
