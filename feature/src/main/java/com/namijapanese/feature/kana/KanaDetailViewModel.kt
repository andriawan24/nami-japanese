package com.namijapanese.feature.kana

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.data.repository.DrawingRepository
import com.namijapanese.core.data.repository.KanaRepository
import com.namijapanese.core.data.repository.ProgressRepository
import com.namijapanese.core.data.repository.SavedKanaDrawing
import com.namijapanese.core.datastore.AuthDataStore
import com.namijapanese.core.model.KanaCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class KanaDetailUiState(
    val character: KanaCharacter? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val savedDrawing: SavedKanaDrawing? = null
)

@HiltViewModel
class KanaDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val kanaRepository: KanaRepository,
    private val progressRepository: ProgressRepository,
    private val drawingRepository: DrawingRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val characterId: String = savedStateHandle["characterId"] ?: ""

    private val _uiState = MutableStateFlow(KanaDetailUiState())
    val uiState: StateFlow<KanaDetailUiState> = _uiState.asStateFlow()

    private var currentOwnerId: String = "local_legacy"

    init {
        viewModelScope.launch {
            currentOwnerId = resolveOwnerId()
        }
        loadCharacter()
        observeDrawing()
    }

    private suspend fun resolveOwnerId(): String {
        val session = authDataStore.userSessionFlow.first()
        return session.userId ?: session.googleUserId ?: session.email ?: "local_legacy"
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = _uiState.value.character == null) }

            val ownerId = resolveOwnerId()
            val character = kanaRepository.getCharacterById(characterId)
            val progress = progressRepository.getProgress(ownerId, characterId)

            _uiState.update {
                it.copy(
                    character = character?.copy(
                        isLearned = progress?.isCompleted ?: false,
                        practiceCount = progress?.practiceCount ?: 0,
                        bestScore = progress?.bestScore ?: 0,
                        bestWritingScore = progress?.bestWritingScore ?: 0,
                        bestQuizScore = progress?.bestQuizScore ?: 0,
                        lastPracticedAt = progress?.lastPracticedAt
                    ),
                    isLoading = false
                )
            }
        }
    }

    private fun observeDrawing() {
        viewModelScope.launch {
            val ownerId = resolveOwnerId()
            drawingRepository.observeDrawing(ownerId, characterId).collect { drawing ->
                _uiState.update { it.copy(savedDrawing = drawing) }
            }
        }
    }

    fun refreshProgress() {
        viewModelScope.launch {
            val ownerId = resolveOwnerId()
            val progress = progressRepository.getProgress(ownerId, characterId)
            val currentCharacter = _uiState.value.character
            if (currentCharacter != null) {
                _uiState.update {
                    it.copy(
                        character = currentCharacter.copy(
                            isLearned = progress?.isCompleted ?: false,
                            practiceCount = progress?.practiceCount ?: 0,
                            bestScore = progress?.bestScore ?: 0,
                            bestWritingScore = progress?.bestWritingScore ?: 0,
                            bestQuizScore = progress?.bestQuizScore ?: 0,
                            lastPracticedAt = progress?.lastPracticedAt
                        )
                    )
                }
            }
        }
    }
}
