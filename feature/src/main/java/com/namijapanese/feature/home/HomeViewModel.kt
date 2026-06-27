package com.namijapanese.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.data.repository.ProgressRepository
import com.namijapanese.core.data.repository.StreakRepository
import com.namijapanese.core.datastore.AuthDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

data class WeeklyProgressUiState(
    val percent: Int = 0,
    val activeDays: Set<DayOfWeek> = emptySet(),
    val currentDay: DayOfWeek = LocalDate.now().dayOfWeek,
    val isLoading: Boolean = false
)

data class HomeUiState(
    val learnedCount: Int = 0,
    val totalCount: Int = 92,
    val currentStreak: Int = 0,
    val isLoading: Boolean = true,
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val weeklyProgress: WeeklyProgressUiState = WeeklyProgressUiState()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val progressRepository: ProgressRepository,
    private val streakRepository: StreakRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
        loadWeeklyProgress()
        observeUserSession()
    }

    fun onResume() {
        loadWeeklyProgress()
        loadData()
    }

    private fun observeUserSession() {
        viewModelScope.launch {
            authDataStore.userSessionFlow.collect { session ->
                _uiState.update {
                    it.copy(
                        displayName = session.displayName,
                        email = session.email,
                        photoUrl = session.photoUrl
                    )
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val learnedCount = progressRepository.getLearnedCount()
            val streak = streakRepository.getStreak()

            _uiState.update {
                it.copy(
                    learnedCount = learnedCount,
                    currentStreak = streak?.currentStreak ?: 0,
                    isLoading = false
                )
            }
        }
    }

    private fun loadWeeklyProgress() {
        viewModelScope.launch {
            _uiState.update { it.copy(weeklyProgress = it.weeklyProgress.copy(isLoading = true)) }

            val zone = ZoneId.systemDefault()
            val now = LocalDate.now()
            val weekStart = now.with(DayOfWeek.MONDAY)
            val weekEnd = now.with(DayOfWeek.SUNDAY)

            val startMillis = LocalDateTime.of(weekStart, LocalTime.MIN)
                .atZone(zone).toInstant().toEpochMilli()
            val endMillis = LocalDateTime.of(weekEnd, LocalTime.MAX)
                .atZone(zone).toInstant().toEpochMilli()

            val sessions = progressRepository.getPracticeSessionsBetween(startMillis, endMillis)

            val activeDays = sessions.map { session ->
                java.time.Instant.ofEpochMilli(session.practicedAt)
                    .atZone(zone).toLocalDate().dayOfWeek
            }.toSet()

            val percent = (activeDays.size * 100) / 7

            _uiState.update {
                it.copy(
                    weeklyProgress = WeeklyProgressUiState(
                        percent = percent,
                        activeDays = activeDays,
                        currentDay = now.dayOfWeek,
                        isLoading = false
                    )
                )
            }
        }
    }
}
