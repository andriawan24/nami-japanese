package com.namijapanese.feature.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.data.repository.ProgressRepository
import com.namijapanese.core.data.repository.StreakRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProgressUiState(
    val learnedHiragana: Int = 0,
    val learnedKatakana: Int = 0,
    val totalHiragana: Int = 46,
    val totalKatakana: Int = 46,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalPracticeDays: Int = 0,
    val isLoading: Boolean = true
) {
    val totalLearned: Int get() = learnedHiragana + learnedKatakana
    val totalCharacters: Int get() = totalHiragana + totalKatakana
    val overallProgress: Float get() = if (totalCharacters > 0) totalLearned.toFloat() / totalCharacters else 0f
}

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val progressRepository: ProgressRepository,
    private val streakRepository: StreakRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            val learned = progressRepository.getAllLearned()
            val streak = streakRepository.getStreak()
            
            _uiState.update {
                it.copy(
                    learnedHiragana = learned.count { it.characterId.startsWith("h_") },
                    learnedKatakana = learned.count { it.characterId.startsWith("k_") },
                    currentStreak = streak?.currentStreak ?: 0,
                    longestStreak = streak?.longestStreak ?: 0,
                    totalPracticeDays = streak?.totalPracticeDays ?: 0,
                    isLoading = false
                )
            }
        }
    }
}
