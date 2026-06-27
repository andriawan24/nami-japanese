package com.namijapanese.feature.kana

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.data.repository.KanaRepository
import com.namijapanese.core.data.repository.ProgressRepository
import com.namijapanese.core.model.KanaCharacter
import com.namijapanese.core.model.KanaGroup
import com.namijapanese.core.model.KanaStatus
import com.namijapanese.core.model.KanaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class KanaListUiState(
    val allCharacters: List<KanaCharacter> = emptyList(),
    val filteredCharacters: List<KanaCharacter> = emptyList(),
    val selectedGroup: KanaGroup? = KanaGroup.A,
    val completedGroups: Set<KanaGroup> = emptySet(),
    val isLoading: Boolean = true
)

@HiltViewModel
class KanaListViewModel @Inject constructor(
    private val kanaRepository: KanaRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(KanaListUiState())
    val uiState: StateFlow<KanaListUiState> = _uiState.asStateFlow()

    private var currentType: KanaType? = null
    private var userManuallySelectedGroup = false

    fun setType(type: KanaType) {
        val typeChanged = currentType != type
        currentType = type

        if (typeChanged) {
            userManuallySelectedGroup = false
            _uiState.update { it.copy(selectedGroup = KanaGroup.A, isLoading = true) }
        }

        if (typeChanged || _uiState.value.allCharacters.isEmpty()) {
            loadCharacters(preserveGroup = !typeChanged)
        }
    }

    fun filterByGroup(group: KanaGroup?) {
        userManuallySelectedGroup = true
        _uiState.update { state ->
            state.copy(
                selectedGroup = group,
                filteredCharacters = applyGroupFilter(state.allCharacters, group)
            )
        }
    }

    fun onResume() {
        refreshProgress(allowAutoAdvance = true)
    }

    fun refreshProgress(allowAutoAdvance: Boolean = false) {
        viewModelScope.launch {
            val characters = loadCharactersWithProgress()
            val completedGroups = computeCompletedGroups(characters)
            var selectedGroup = _uiState.value.selectedGroup

            if (allowAutoAdvance && selectedGroup != null && selectedGroup in completedGroups) {
                selectedGroup = findNextIncompleteGroup(selectedGroup, completedGroups)
            }

            if (allowAutoAdvance && selectedGroup == null && !userManuallySelectedGroup) {
                selectedGroup = findFirstIncompleteGroup(completedGroups)
            }

            _uiState.update { state ->
                state.copy(
                    allCharacters = characters,
                    filteredCharacters = applyGroupFilter(characters, selectedGroup),
                    selectedGroup = selectedGroup,
                    completedGroups = completedGroups,
                    isLoading = false
                )
            }
        }
    }

    private fun loadCharacters(preserveGroup: Boolean) {
        viewModelScope.launch {
            val characters = loadCharactersWithProgress()
            val completedGroups = computeCompletedGroups(characters)
            val group = if (preserveGroup) _uiState.value.selectedGroup
            else findFirstIncompleteGroup(completedGroups)

            _uiState.update {
                it.copy(
                    allCharacters = characters,
                    filteredCharacters = applyGroupFilter(characters, group),
                    selectedGroup = group,
                    completedGroups = completedGroups,
                    isLoading = false
                )
            }
        }
    }

    private fun computeCompletedGroups(characters: List<KanaCharacter>): Set<KanaGroup> {
        return KanaGroup.entries.filter { group ->
            val groupChars = characters.filter { it.group == group }
            groupChars.isNotEmpty() && groupChars.all { it.isCompleted }
        }.toSet()
    }

    private fun findFirstIncompleteGroup(completedGroups: Set<KanaGroup>): KanaGroup {
        return KanaGroup.entries.firstOrNull { it !in completedGroups } ?: KanaGroup.A
    }

    private fun findNextIncompleteGroup(
        current: KanaGroup,
        completedGroups: Set<KanaGroup>
    ): KanaGroup? {
        val allGroups = KanaGroup.entries
        val currentIndex = allGroups.indexOf(current)

        // Check groups after current
        for (i in (currentIndex + 1) until allGroups.size) {
            if (allGroups[i] !in completedGroups) return allGroups[i]
        }
        // Wrap around to beginning
        for (i in 0 until currentIndex) {
            if (allGroups[i] !in completedGroups) return allGroups[i]
        }
        // All completed
        return null
    }

    private fun applyGroupFilter(chars: List<KanaCharacter>, group: KanaGroup?): List<KanaCharacter> {
        return if (group == null) chars else chars.filter { it.group == group }
    }

    private suspend fun loadCharactersWithProgress(): List<KanaCharacter> {
        val characters = kanaRepository.getCharacters(currentType ?: KanaType.HIRAGANA)
        val allProgress = progressRepository.getAllProgress().associateBy { it.characterId }

        return characters.map { char ->
            val progress = allProgress[char.id]
            char.copy(
                isLearned = progress?.isCompleted ?: false,
                practiceCount = progress?.practiceCount ?: 0,
                bestScore = progress?.bestScore ?: 0,
                bestWritingScore = progress?.bestWritingScore ?: 0,
                bestQuizScore = progress?.bestQuizScore ?: 0,
                lastPracticedAt = progress?.lastPracticedAt
            )
        }
    }
}
