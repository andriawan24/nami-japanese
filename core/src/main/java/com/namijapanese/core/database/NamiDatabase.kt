package com.namijapanese.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.namijapanese.core.database.dao.DailyStreakDao
import com.namijapanese.core.database.dao.KanaDrawingDao
import com.namijapanese.core.database.dao.LearningSessionDao
import com.namijapanese.core.database.dao.PracticeSessionDao
import com.namijapanese.core.database.dao.UserProgressDao
import com.namijapanese.core.database.entity.DailyStreakEntity
import com.namijapanese.core.database.entity.KanaDrawingEntity
import com.namijapanese.core.database.entity.LearningSessionEntity
import com.namijapanese.core.database.entity.PracticeSessionEntity
import com.namijapanese.core.database.entity.UserProgressEntity

@Database(
    entities = [
        UserProgressEntity::class,
        DailyStreakEntity::class,
        LearningSessionEntity::class,
        KanaDrawingEntity::class,
        PracticeSessionEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class NamiDatabase : RoomDatabase() {
    abstract fun userProgressDao(): UserProgressDao
    abstract fun dailyStreakDao(): DailyStreakDao
    abstract fun learningSessionDao(): LearningSessionDao
    abstract fun kanaDrawingDao(): KanaDrawingDao
    abstract fun practiceSessionDao(): PracticeSessionDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `kana_drawings` (
                        `character_id` TEXT NOT NULL,
                        `strokes_json` TEXT NOT NULL,
                        `canvas_width` REAL NOT NULL,
                        `canvas_height` REAL NOT NULL,
                        `updated_at` INTEGER NOT NULL,
                        PRIMARY KEY(`character_id`)
                    )"""
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `practice_sessions` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `character_id` TEXT NOT NULL,
                        `practiced_at` INTEGER NOT NULL,
                        `score` INTEGER,
                        `passed` INTEGER NOT NULL
                    )"""
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_practice_sessions_practiced_at` ON `practice_sessions` (`practiced_at`)"
                )
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE `user_progress` ADD COLUMN `best_writing_score` INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE `user_progress` ADD COLUMN `best_quiz_score` INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
