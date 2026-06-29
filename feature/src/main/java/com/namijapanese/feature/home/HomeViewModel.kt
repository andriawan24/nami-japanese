package com.namijapanese.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.data.repository.KanaRepository
import com.namijapanese.core.data.repository.ProgressRepository
import com.namijapanese.core.data.repository.StreakRepository
import com.namijapanese.core.datastore.AuthDataStore
import com.namijapanese.core.model.KanaCharacter
import com.namijapanese.core.model.KanaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

enum class ContinueLearningAction {
    OpenKanaDetail,
    OpenKanaList
}

data class ContinueLearningUiState(
    val title: String = "Continue Learning",
    val subtitle: String = "Start your journey",
    val characterId: String? = null,
    val character: String? = null,
    val action: ContinueLearningAction = ContinueLearningAction.OpenKanaList,
    val isLoading: Boolean = true
) {
    companion object {
        val Empty = ContinueLearningUiState(isLoading = false)
    }
}

data class WeeklyProgressUiState(
    val percent: Int = 0,
    val activeDays: Set<DayOfWeek> = emptySet(),
    val currentDay: DayOfWeek = LocalDate.now().dayOfWeek,
    val isLoading: Boolean = false
)

data class DailyGoalUiState(
    val target: Int = 5,
    val completed: Int = 0,
    val progress: Float = 0f,
    val isCompleted: Boolean = false,
    val message: String = "Start with one kana today.",
    val isLoading: Boolean = true
)

data class HomeUiState(
    val learnedCount: Int = 0,
    val totalCount: Int = 92,
    val currentStreak: Int = 0,
    val isLoading: Boolean = true,
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val weeklyProgress: WeeklyProgressUiState = WeeklyProgressUiState(),
    val continueLearning: ContinueLearningUiState = ContinueLearningUiState.Empty,
    val dailyGoal: DailyGoalUiState = DailyGoalUiState()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val progressRepository: ProgressRepository,
    private val streakRepository: StreakRepository,
    private val authDataStore: AuthDataStore,
    private val kanaRepository: KanaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentOwnerId: String = "local_legacy"

    // Cached static kana data - loaded once
    private var cachedAllKana: List<KanaCharacter>? = null

    // Debounce for onResume
    private var lastRefreshAt = 0L
    private companion object {
        const val REFRESH_DEBOUNCE_MS = 800L
    }

    init {
        observeUserSession()
        loadAllHomeData()
    }

    fun onResume() {
        val now = System.currentTimeMillis()
        if (now - lastRefreshAt < REFRESH_DEBOUNCE_MS) return
        lastRefreshAt = now
        loadAllHomeData()
    }

    private fun observeUserSession() {
        viewModelScope.launch {
            authDataStore.userSessionFlow.collect { session ->
                currentOwnerId = session.userId
                    ?: session.googleUserId
                    ?: session.email
                    ?: "local_legacy"
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

    private suspend fun resolveOwnerId(): String {
        val session = authDataStore.userSessionFlow.first()
        return session.userId
            ?: session.googleUserId
            ?: session.email
            ?: "local_legacy"
    }

    private fun loadAllHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Resolve owner ID once
            val ownerId = resolveOwnerId()
            currentOwnerId = ownerId

            // Ensure kana cache is loaded once
            if (cachedAllKana == null) {
                cachedAllKana = withContext(Dispatchers.Default) {
                    kanaRepository.getCharacters(KanaType.HIRAGANA) +
                            kanaRepository.getCharacters(KanaType.KATAKANA)
                }
            }

            // Run all heavy work on Default dispatcher in one block
            val result = withContext(Dispatchers.Default) {
                val learnedCount = progressRepository.getLearnedCount(ownerId)
                val streak = streakRepository.getStreak(ownerId)

                // Weekly progress calculation
                val zone = ZoneId.systemDefault()
                val now = LocalDate.now()
                val weekStart = now.with(DayOfWeek.MONDAY)
                val weekEnd = now.with(DayOfWeek.SUNDAY)
                val startMillis = LocalDateTime.of(weekStart, LocalTime.MIN)
                    .atZone(zone).toInstant().toEpochMilli()
                val endMillis = LocalDateTime.of(weekEnd, LocalTime.MAX)
                    .atZone(zone).toInstant().toEpochMilli()
                val sessions = progressRepository.getPracticeSessionsBetween(ownerId, startMillis, endMillis)
                val activeDays = sessions.map { session ->
                    java.time.Instant.ofEpochMilli(session.practicedAt)
                        .atZone(zone).toLocalDate().dayOfWeek
                }.toSet()
                val weeklyPercent = (activeDays.size * 100) / 7

                // Daily goal calculation
                val today = LocalDate.now()
                val startOfDay = LocalDateTime.of(today, LocalTime.MIN)
                    .atZone(zone).toInstant().toEpochMilli()
                val startOfTomorrow = LocalDateTime.of(today.plusDays(1), LocalTime.MIN)
                    .atZone(zone).toInstant().toEpochMilli()
                val completedToday = progressRepository.getTodayLearningSessionCount(ownerId, startOfDay, startOfTomorrow)

                // Continue learning calculation
                val allProgress = progressRepository.getAllProgress(ownerId)
                val progressMap = allProgress.associateBy { it.characterId }
                val allKana = cachedAllKana ?: emptyList()
                val incompleteKana = allKana.filter { kana ->
                    val progress = progressMap[kana.id]
                    val totalScore = (progress?.bestWritingScore ?: 0) + (progress?.bestQuizScore ?: 0)
                    totalScore < 90
                }

                DataResult(
                    learnedCount = learnedCount,
                    currentStreak = streak?.currentStreak ?: 0,
                    weeklyPercent = weeklyPercent,
                    activeDays = activeDays,
                    currentDay = now.dayOfWeek,
                    completedToday = completedToday,
                    incompleteKana = incompleteKana,
                    progressMap = progressMap
                )
            }

            // Build continue learning state
            val continueState = buildContinueLearning(result.incompleteKana, result.progressMap)

            // Single state update with all results
            val dailyTarget = 5
            val dailyProgress = (result.completedToday.toFloat() / dailyTarget).coerceIn(0f, 1f)
            val isDailyCompleted = result.completedToday >= dailyTarget
            val dailyMessage = when {
                result.completedToday == 0 -> "Start with one kana today."
                result.completedToday < dailyTarget -> "Keep going, you're building momentum."
                else -> "Daily goal completed."
            }

            _uiState.update {
                it.copy(
                    learnedCount = result.learnedCount,
                    currentStreak = result.currentStreak,
                    isLoading = false,
                    weeklyProgress = WeeklyProgressUiState(
                        percent = result.weeklyPercent,
                        activeDays = result.activeDays,
                        currentDay = result.currentDay,
                        isLoading = false
                    ),
                    dailyGoal = DailyGoalUiState(
                        target = dailyTarget,
                        completed = result.completedToday,
                        progress = dailyProgress,
                        isCompleted = isDailyCompleted,
                        message = dailyMessage,
                        isLoading = false
                    ),
                    continueLearning = continueState
                )
            }
        }
    }

    private fun buildContinueLearning(
        incompleteKana: List<KanaCharacter>,
        progressMap: Map<String, com.namijapanese.core.model.UserProgress>
    ): ContinueLearningUiState {
        if (incompleteKana.isEmpty()) {
            return ContinueLearningUiState(
                title = "Continue Learning",
                subtitle = "All kana mastered",
                characterId = null,
                character = null,
                action = ContinueLearningAction.OpenKanaList,
                isLoading = false
            )
        }

        // Priority: kana with writing done but quiz not done
        val writingDoneQuizNot = incompleteKana.firstOrNull { kana ->
            val progress = progressMap[kana.id]
            val writingScore = progress?.bestWritingScore ?: 0
            val quizScore = progress?.bestQuizScore ?: 0
            writingScore > 0 && quizScore == 0
        }

        return if (writingDoneQuizNot != null) {
            ContinueLearningUiState(
                title = "Continue Learning",
                subtitle = "Finish quiz for ${writingDoneQuizNot.character}",
                characterId = writingDoneQuizNot.id,
                character = writingDoneQuizNot.character,
                action = ContinueLearningAction.OpenKanaDetail,
                isLoading = false
            )
        } else {
            val nextKana = incompleteKana.first()
            ContinueLearningUiState(
                title = "Continue Learning",
                subtitle = "Continue with ${nextKana.character}",
                characterId = nextKana.id,
                character = nextKana.character,
                action = ContinueLearningAction.OpenKanaDetail,
                isLoading = false
            )
        }
    }

    private data class DataResult(
        val learnedCount: Int,
        val currentStreak: Int,
        val weeklyPercent: Int,
        val activeDays: Set<DayOfWeek>,
        val currentDay: DayOfWeek,
        val completedToday: Int,
        val incompleteKana: List<KanaCharacter>,
        val progressMap: Map<String, com.namijapanese.core.model.UserProgress>
    )
}
