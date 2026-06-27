package com.namijapanese.feature.writing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.data.repository.DrawingRepository
import com.namijapanese.core.data.repository.KanaRepository
import com.namijapanese.core.data.repository.ProgressRepository
import com.namijapanese.core.data.repository.StreakRepository
import com.namijapanese.core.data.repository.SavedStroke
import com.namijapanese.core.data.repository.SavedStrokePoint
import com.namijapanese.core.model.KanaCharacter
import com.namijapanese.core.model.UserProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class Stroke(val points: List<Pair<Float, Float>>)

data class WritingUiState(
    val character: KanaCharacter? = null,
    val existingProgress: UserProgress? = null,
    val strokes: List<Stroke> = emptyList(),
    val currentStroke: List<Pair<Float, Float>> = emptyList(),
    val isCompleted: Boolean = false,
    val isSaving: Boolean = false,
    val isLoading: Boolean = true,
    val strokeCount: Int = 0,
    val score: Int? = null,
    val passed: Boolean? = null,
    val feedbackMessage: String? = null,
    val scoringMode: String? = null,
    val errorMessage: String? = null,
    val canvasWidth: Float = 0f,
    val canvasHeight: Float = 0f
)

@HiltViewModel
class WritingPracticeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val kanaRepository: KanaRepository,
    private val progressRepository: ProgressRepository,
    private val streakRepository: StreakRepository,
    private val drawingRepository: DrawingRepository
) : ViewModel() {

    private val characterId: String = savedStateHandle["characterId"] ?: ""

    private val _uiState = MutableStateFlow(WritingUiState())
    val uiState: StateFlow<WritingUiState> = _uiState.asStateFlow()

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            try {
                val character = kanaRepository.getCharacterById(characterId)
                val existingProgress = progressRepository.getProgress(characterId)
                _uiState.update {
                    it.copy(
                        character = character,
                        existingProgress = existingProgress,
                        isLoading = false
                    )
                }
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load character."
                    )
                }
            }
        }
    }

    fun updateCanvasSize(width: Float, height: Float) {
        if (width > 0f && height > 0f) {
            _uiState.update { it.copy(canvasWidth = width, canvasHeight = height) }
        }
    }

    fun startStroke(x: Float, y: Float) {
        _uiState.update { it.copy(currentStroke = listOf(x to y), errorMessage = null) }
    }

    fun continueStroke(x: Float, y: Float) {
        _uiState.update { state ->
            val last = state.currentStroke.lastOrNull()
            if (last != null) {
                val dx = x - last.first
                val dy = y - last.second
                if ((dx * dx + dy * dy) < 16f) return@update state
                if (state.currentStroke.size >= 300) return@update state
            }
            state.copy(currentStroke = state.currentStroke + (x to y))
        }
    }

    fun endStroke() {
        _uiState.update { state ->
            if (state.currentStroke.isNotEmpty()) {
                state.copy(
                    strokes = state.strokes + Stroke(state.currentStroke),
                    currentStroke = emptyList(),
                    strokeCount = state.strokeCount + 1
                )
            } else state
        }
    }

    fun clearCanvas() {
        _uiState.update {
            it.copy(
                strokes = emptyList(),
                currentStroke = emptyList(),
                isCompleted = false,
                isSaving = false,
                strokeCount = 0,
                score = null,
                passed = null,
                feedbackMessage = null,
                scoringMode = null,
                errorMessage = null
            )
        }
    }

    fun undoLastStroke() {
        _uiState.update { state ->
            if (state.strokes.isNotEmpty()) {
                state.copy(
                    strokes = state.strokes.dropLast(1),
                    strokeCount = state.strokeCount - 1
                )
            } else state
        }
    }

    fun completePractice() {
        val state = _uiState.value
        if (state.character == null || state.strokes.isEmpty() || state.isSaving || state.isCompleted) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            try {
                val character = state.character

                val simplified = withContext(Dispatchers.Default) {
                    simplifiedStrokesForScoring(state.strokes)
                }

                val score = withContext(Dispatchers.Default) {
                    calculateHeuristicScore(simplified, character, state.canvasWidth, state.canvasHeight)
                }

                val passed = score >= 80
                val message = if (passed) {
                    "Nice work! Your writing practice passed."
                } else {
                    "Try writing bigger and closer to the guide."
                }

                progressRepository.updateWritingScore(characterId, score)
                streakRepository.updateStreak()
                progressRepository.recordPracticeSession(
                    characterId = characterId,
                    score = score,
                    passed = passed
                )

                // Save drawing strokes for Kana Detail visual association
                val savedStrokes = state.strokes.map { stroke ->
                    SavedStroke(
                        points = stroke.points.map { (x, y) -> SavedStrokePoint(x, y) }
                    )
                }
                drawingRepository.saveDrawing(
                    characterId = characterId,
                    strokes = savedStrokes,
                    canvasWidth = state.canvasWidth,
                    canvasHeight = state.canvasHeight
                )

                _uiState.update {
                    it.copy(
                        isCompleted = true,
                        isSaving = false,
                        score = score,
                        passed = passed,
                        feedbackMessage = message,
                        scoringMode = "Basic scoring used"
                    )
                }
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isCompleted = false,
                        errorMessage = "Practice could not be completed. Please try again."
                    )
                }
            }
        }
    }

    private fun simplifiedStrokesForScoring(strokes: List<Stroke>): List<Stroke> {
        return strokes
            .filter { it.points.size >= 2 }
            .take(12)
            .map { stroke ->
                val points = if (stroke.points.size <= 120) {
                    stroke.points
                } else {
                    val step = (stroke.points.size / 120).coerceAtLeast(1)
                    stroke.points.filterIndexed { index, _ -> index % step == 0 }.take(120)
                }
                Stroke(points)
            }
    }

    // ==================== HEURISTIC SCORING ONLY ====================

    private fun calculateHeuristicScore(
        strokes: List<Stroke>,
        character: KanaCharacter,
        canvasWidth: Float,
        canvasHeight: Float
    ): Int {
        var score = 0

        // Has strokes: +20
        if (strokes.isNotEmpty()) score += 20

        // Enough total points: +20
        val totalPoints = strokes.sumOf { it.points.size }
        if (totalPoints >= 50) score += 20
        else if (totalPoints >= 20) score += 10

        // Stroke count close to expected: +25
        val expected = character.strokeCount.coerceAtLeast(1)
        val drawn = strokes.size
        val strokeDiff = kotlin.math.abs(expected - drawn)
        when {
            strokeDiff == 0 -> score += 25
            strokeDiff == 1 -> score += 20
            strokeDiff == 2 -> score += 12
            else -> score += 5
        }

        // Drawing size/coverage: +20
        val bounds = calculateBoundingBox(strokes)
        if (bounds != null) {
            val width = bounds.second.first - bounds.first.first
            val height = bounds.second.second - bounds.first.second
            val area = width * height
            val canvasArea = canvasWidth * canvasHeight
            if (canvasArea > 0) {
                val coverage = area / canvasArea
                when {
                    coverage >= 0.15f -> score += 20
                    coverage >= 0.05f -> score += 12
                    coverage >= 0.01f -> score += 5
                }
            } else {
                if (area > 10000f) score += 20
                else if (area > 3000f) score += 12
                else if (area > 500f) score += 5
            }
        }

        // Center position: +15
        if (bounds != null && canvasWidth > 0 && canvasHeight > 0) {
            val centerX = (bounds.first.first + bounds.second.first) / 2f
            val centerY = (bounds.first.second + bounds.second.second) / 2f
            val canvasCenterX = canvasWidth / 2f
            val canvasCenterY = canvasHeight / 2f
            val offsetX = kotlin.math.abs(centerX - canvasCenterX) / canvasWidth
            val offsetY = kotlin.math.abs(centerY - canvasCenterY) / canvasHeight
            val offset = (offsetX + offsetY) / 2f
            when {
                offset < 0.15f -> score += 15
                offset < 0.30f -> score += 10
                offset < 0.45f -> score += 5
            }
        } else {
            score += 5
        }

        return score.coerceIn(0, 100)
    }

    private fun calculateBoundingBox(strokes: List<Stroke>): Pair<Pair<Float, Float>, Pair<Float, Float>>? {
        if (strokes.isEmpty()) return null
        var minX = Float.MAX_VALUE
        var minY = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var maxY = Float.MIN_VALUE
        for (stroke in strokes) {
            for ((x, y) in stroke.points) {
                if (x < minX) minX = x
                if (y < minY) minY = y
                if (x > maxX) maxX = x
                if (y > maxY) maxY = y
            }
        }
        if (minX > maxX || minY > maxY) return null
        return Pair(minX to minY, maxX to maxY)
    }
}
