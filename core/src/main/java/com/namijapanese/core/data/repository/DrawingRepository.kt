package com.namijapanese.core.data.repository

import com.namijapanese.core.database.dao.KanaDrawingDao
import com.namijapanese.core.database.entity.KanaDrawingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrawingRepository @Inject constructor(
    private val drawingDao: KanaDrawingDao
) {
    fun observeDrawing(ownerId: String, characterId: String): Flow<SavedKanaDrawing?> {
        return drawingDao.observeDrawing(ownerId, characterId).map { entity ->
            entity?.toDomain()
        }
    }

    suspend fun getDrawing(ownerId: String, characterId: String): SavedKanaDrawing? {
        return drawingDao.getDrawing(ownerId, characterId)?.toDomain()
    }

    suspend fun saveDrawing(
        ownerId: String,
        characterId: String,
        strokes: List<SavedStroke>,
        canvasWidth: Float,
        canvasHeight: Float
    ) {
        if (strokes.isEmpty()) return
        val entity = KanaDrawingEntity(
            ownerId = ownerId,
            characterId = characterId,
            strokesJson = DrawingSerializer.encode(strokes),
            canvasWidth = canvasWidth,
            canvasHeight = canvasHeight,
            updatedAt = System.currentTimeMillis()
        )
        drawingDao.upsert(entity)
    }

    private fun KanaDrawingEntity.toDomain() = SavedKanaDrawing(
        characterId = characterId,
        strokes = DrawingSerializer.decode(strokesJson),
        canvasWidth = canvasWidth,
        canvasHeight = canvasHeight,
        updatedAt = updatedAt
    )
}
