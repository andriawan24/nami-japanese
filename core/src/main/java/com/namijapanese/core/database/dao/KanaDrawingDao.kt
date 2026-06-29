package com.namijapanese.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namijapanese.core.database.entity.KanaDrawingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KanaDrawingDao {
    @Query("SELECT * FROM kana_drawings WHERE owner_id = :ownerId AND character_id = :characterId")
    fun observeDrawing(ownerId: String, characterId: String): Flow<KanaDrawingEntity?>

    @Query("SELECT * FROM kana_drawings WHERE owner_id = :ownerId AND character_id = :characterId")
    suspend fun getDrawing(ownerId: String, characterId: String): KanaDrawingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(drawing: KanaDrawingEntity)

    @Query("UPDATE kana_drawings SET owner_id = :newOwnerId WHERE owner_id = :oldOwnerId")
    suspend fun migrateOwnerId(oldOwnerId: String, newOwnerId: String)
}
