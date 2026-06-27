package com.namijapanese.app.di

import android.content.Context
import androidx.room.Room
import com.namijapanese.core.database.NamiDatabase
import com.namijapanese.core.database.dao.DailyStreakDao
import com.namijapanese.core.database.dao.KanaDrawingDao
import com.namijapanese.core.database.dao.LearningSessionDao
import com.namijapanese.core.database.dao.PracticeSessionDao
import com.namijapanese.core.database.dao.UserProgressDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NamiDatabase {
        return Room.databaseBuilder(
            context,
            NamiDatabase::class.java,
            "nami_japanese_database"
        )
            .addMigrations(NamiDatabase.MIGRATION_1_2, NamiDatabase.MIGRATION_2_3, NamiDatabase.MIGRATION_3_4)
            .build()
    }

    @Provides
    fun provideUserProgressDao(database: NamiDatabase): UserProgressDao =
        database.userProgressDao()

    @Provides
    fun provideDailyStreakDao(database: NamiDatabase): DailyStreakDao =
        database.dailyStreakDao()

    @Provides
    fun provideLearningSessionDao(database: NamiDatabase): LearningSessionDao =
        database.learningSessionDao()

    @Provides
    fun provideKanaDrawingDao(database: NamiDatabase): KanaDrawingDao =
        database.kanaDrawingDao()

    @Provides
    fun providePracticeSessionDao(database: NamiDatabase): PracticeSessionDao =
        database.practiceSessionDao()
}
