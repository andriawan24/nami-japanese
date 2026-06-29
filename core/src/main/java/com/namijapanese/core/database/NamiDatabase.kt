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
    version = 5,
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

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // user_progress: add owner_id, recreate with composite primary key
                db.execSQL("ALTER TABLE `user_progress` ADD COLUMN `owner_id` TEXT NOT NULL DEFAULT 'local_legacy'")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_user_progress_owner_id` ON `user_progress` (`owner_id`)")
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `user_progress_new` (
                        `owner_id` TEXT NOT NULL,
                        `character_id` TEXT NOT NULL,
                        `is_learned` INTEGER NOT NULL,
                        `practice_count` INTEGER NOT NULL,
                        `best_score` INTEGER NOT NULL,
                        `best_writing_score` INTEGER NOT NULL DEFAULT 0,
                        `best_quiz_score` INTEGER NOT NULL DEFAULT 0,
                        `last_practiced_at` INTEGER,
                        `created_at` INTEGER NOT NULL,
                        PRIMARY KEY(`owner_id`, `character_id`)
                    )
                """)
                db.execSQL("INSERT INTO `user_progress_new` (`owner_id`, `character_id`, `is_learned`, `practice_count`, `best_score`, `best_writing_score`, `best_quiz_score`, `last_practiced_at`, `created_at`) SELECT `owner_id`, `character_id`, `is_learned`, `practice_count`, `best_score`, `best_writing_score`, `best_quiz_score`, `last_practiced_at`, `created_at` FROM `user_progress`")
                db.execSQL("DROP TABLE `user_progress`")
                db.execSQL("ALTER TABLE `user_progress_new` RENAME TO `user_progress`")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_user_progress_owner_id` ON `user_progress` (`owner_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_user_progress_is_learned` ON `user_progress` (`is_learned`)")

                // kana_drawings: add owner_id, recreate with composite primary key
                db.execSQL("ALTER TABLE `kana_drawings` ADD COLUMN `owner_id` TEXT NOT NULL DEFAULT 'local_legacy'")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_kana_drawings_owner_id` ON `kana_drawings` (`owner_id`)")
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `kana_drawings_new` (
                        `owner_id` TEXT NOT NULL,
                        `character_id` TEXT NOT NULL,
                        `strokes_json` TEXT NOT NULL,
                        `canvas_width` REAL NOT NULL,
                        `canvas_height` REAL NOT NULL,
                        `updated_at` INTEGER NOT NULL,
                        PRIMARY KEY(`owner_id`, `character_id`)
                    )
                """)
                db.execSQL("INSERT INTO `kana_drawings_new` (`owner_id`, `character_id`, `strokes_json`, `canvas_width`, `canvas_height`, `updated_at`) SELECT `owner_id`, `character_id`, `strokes_json`, `canvas_width`, `canvas_height`, `updated_at` FROM `kana_drawings`")
                db.execSQL("DROP TABLE `kana_drawings`")
                db.execSQL("ALTER TABLE `kana_drawings_new` RENAME TO `kana_drawings`")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_kana_drawings_owner_id` ON `kana_drawings` (`owner_id`)")

                // practice_sessions: add owner_id
                db.execSQL("ALTER TABLE `practice_sessions` ADD COLUMN `owner_id` TEXT NOT NULL DEFAULT 'local_legacy'")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_practice_sessions_owner_id` ON `practice_sessions` (`owner_id`)")

                // learning_sessions: add owner_id
                db.execSQL("ALTER TABLE `learning_sessions` ADD COLUMN `owner_id` TEXT NOT NULL DEFAULT 'local_legacy'")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_learning_sessions_owner_id` ON `learning_sessions` (`owner_id`)")

                // daily_streak: add owner_id, recreate with composite primary key
                db.execSQL("ALTER TABLE `daily_streak` ADD COLUMN `owner_id` TEXT NOT NULL DEFAULT 'local_legacy'")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_daily_streak_owner_id` ON `daily_streak` (`owner_id`)")
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `daily_streak_new` (
                        `owner_id` TEXT NOT NULL,
                        `id` INTEGER NOT NULL DEFAULT 1,
                        `current_streak` INTEGER NOT NULL,
                        `longest_streak` INTEGER NOT NULL,
                        `last_practice_date` TEXT NOT NULL,
                        `total_practice_days` INTEGER NOT NULL,
                        PRIMARY KEY(`owner_id`, `id`)
                    )
                """)
                db.execSQL("INSERT INTO `daily_streak_new` (`owner_id`, `id`, `current_streak`, `longest_streak`, `last_practice_date`, `total_practice_days`) SELECT `owner_id`, `id`, `current_streak`, `longest_streak`, `last_practice_date`, `total_practice_days` FROM `daily_streak`")
                db.execSQL("DROP TABLE `daily_streak`")
                db.execSQL("ALTER TABLE `daily_streak_new` RENAME TO `daily_streak`")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_daily_streak_owner_id` ON `daily_streak` (`owner_id`)")
            }
        }
    }
}
