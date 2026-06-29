package com.namijapanese.feature.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.data.repository.KanaRepository
import com.namijapanese.core.data.repository.ProgressRepository
import com.namijapanese.core.data.repository.StreakRepository
import com.namijapanese.core.datastore.AuthDataStore
import com.namijapanese.core.model.KanaCharacter
import com.namijapanese.core.model.KanaType
import com.namijapanese.core.model.UserProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizQuestion(
    val character: KanaCharacter,
    val options: List<String>,
    val correctAnswer: String
)

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: String? = null,
    val isCorrect: Boolean? = null,
    val score: Int = 0,
    val isComplete: Boolean = false,
    val isLoading: Boolean = true
) {
    val currentQuestion: QuizQuestion? get() = questions.getOrNull(currentIndex)
    val progress: Float get() = if (questions.isNotEmpty()) currentIndex.toFloat() / questions.size else 0f
}

@HiltViewModel
class QuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val kanaRepository: KanaRepository,
    private val progressRepository: ProgressRepository,
    private val streakRepository: StreakRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val quizType: String = savedStateHandle["quizType"] ?: "hiragana"

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val answers = mutableMapOf<Int, Boolean>()
    private var currentOwnerId: String = "local_legacy"

    init {
        viewModelScope.launch {
            currentOwnerId = resolveOwnerId()
        }
        loadQuestions()
    }

    private suspend fun resolveOwnerId(): String {
        val session = authDataStore.userSessionFlow.first()
        return session.userId ?: session.googleUserId ?: session.email ?: "local_legacy"
    }

    private fun loadQuestions() {
        val type = if (quizType == "katakana") KanaType.KATAKANA else KanaType.HIRAGANA
        val allCharacters = kanaRepository.getCharacters(type)
        val selected = allCharacters.shuffled().take(10)

        val questions = selected.map { char ->
            val others = allCharacters.filter { it.id != char.id }.shuffled().take(3)
            QuizQuestion(
                character = char,
                options = (others.map { it.romaji } + char.romaji).shuffled(),
                correctAnswer = char.romaji
            )
        }

        answers.clear()
        _uiState.update { it.copy(questions = questions, isLoading = false) }
    }

    fun selectAnswer(answer: String) {
        val question = _uiState.value.currentQuestion ?: return
        val isCorrect = answer == question.correctAnswer
        answers[_uiState.value.currentIndex] = isCorrect
        _uiState.update {
            it.copy(
                selectedAnswer = answer,
                isCorrect = isCorrect,
                score = if (isCorrect) it.score + 1 else it.score
            )
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentIndex + 1

        if (nextIndex >= state.questions.size) {
            viewModelScope.launch {
                val ownerId = resolveOwnerId()
                val totalQuestions = state.questions.size
                val correctAnswers = answers.values.count { it }
                val quizScorePercent = if (totalQuestions > 0) {
                    (correctAnswers * 100) / totalQuestions
                } else 0

                state.questions.forEachIndexed { index, question ->
                    if (answers[index] == true) {
                        progressRepository.updateQuizScore(ownerId, question.character.id, quizScorePercent)
                    }
                }
                progressRepository.recordLearningSession(
                    ownerId = ownerId,
                    type = "QUIZ",
                    score = quizScorePercent,
                    charactersLearned = correctAnswers
                )
                streakRepository.updateStreak(ownerId)
            }
            _uiState.update { it.copy(isComplete = true) }
        } else {
            _uiState.update { it.copy(currentIndex = nextIndex, selectedAnswer = null, isCorrect = null) }
        }
    }

    fun restartQuiz() {
        answers.clear()
        _uiState.update { QuizUiState() }
        loadQuestions()
    }
}
