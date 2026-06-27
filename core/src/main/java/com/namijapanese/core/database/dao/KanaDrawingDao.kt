package com.namijapanese.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namijapanese.core.database.entity.KanaDrawingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KanaDrawingDao {
    @Query("SELECT * FROM kana_drawings WHERE character_id = :characterId")
    fun observeDrawing(characterId: String): Flow<KanaDrawingEntity?>

    @Query("SELECT * FROM kana_drawings WHERE character_id = :characterId")
    suspend fun getDrawing(characterId: String): KanaDrawingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(drawing: KanaDrawingEntity)
}
