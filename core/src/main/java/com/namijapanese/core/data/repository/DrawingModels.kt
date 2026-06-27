package com.namijapanese.core.data.repository

data class SavedStrokePoint(val x: Float, val y: Float)

data class SavedStroke(val points: List<SavedStrokePoint>)

data class SavedKanaDrawing(
    val characterId: String,
    val strokes: List<SavedStroke>,
    val canvasWidth: Float,
    val canvasHeight: Float,
    val updatedAt: Long
)

object DrawingSerializer {
    fun encode(strokes: List<SavedStroke>): String {
        return strokes.joinToString("|") { stroke ->
            stroke.points.joinToString(";") { "${it.x},${it.y}" }
        }
    }

    fun decode(json: String): List<SavedStroke> {
        if (json.isBlank()) return emptyList()
        return json.split("|").mapNotNull { strokeStr ->
            val points = strokeStr.split(";").mapNotNull { pointStr ->
                val parts = pointStr.split(",")
                if (parts.size == 2) {
                    val x = parts[0].toFloatOrNull()
                    val y = parts[1].toFloatOrNull()
                    if (x != null && y != null) SavedStrokePoint(x, y) else null
                } else null
            }
            if (points.isNotEmpty()) SavedStroke(points) else null
        }
    }
}
