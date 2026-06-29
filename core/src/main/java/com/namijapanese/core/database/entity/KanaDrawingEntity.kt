package com.namijapanese.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "kana_drawings",
    primaryKeys = ["owner_id", "character_id"],
    indices = [
        Index(value = ["owner_id"])
    ]
)
data class KanaDrawingEntity(
    @ColumnInfo(name = "owner_id")
    val ownerId: String,

    @ColumnInfo(name = "character_id")
    val characterId: String,

    @ColumnInfo(name = "strokes_json")
    val strokesJson: String,

    @ColumnInfo(name = "canvas_width")
    val canvasWidth: Float,

    @ColumnInfo(name = "canvas_height")
    val canvasHeight: Float,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)
