package com.namijapanese.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.datastore.AuthDataStore
import com.namijapanese.core.datastore.LocalUserSession
import com.namijapanese.core.datastore.UserPreferences
import com.namijapanese.core.datastore.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: UserPreferencesManager,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserPreferences())
    val uiState: StateFlow<UserPreferences> = _uiState.asStateFlow()

    private val _userSession = MutableStateFlow(LocalUserSession())
    val userSession: StateFlow<LocalUserSession> = _userSession.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesManager.userPreferences.collect { prefs ->
                _uiState.update { prefs }
            }
        }
        viewModelScope.launch {
            authDataStore.userSessionFlow.collect { session ->
                _userSession.update { session }
            }
        }
    }

    fun setDailyReminder(enabled: Boolean) {
        viewModelScope.launch { preferencesManager.setDailyReminderEnabled(enabled) }
    }

    fun setSound(enabled: Boolean) {
        viewModelScope.launch { preferencesManager.setSoundEnabled(enabled) }
    }

    fun setHapticFeedback(enabled: Boolean) {
        viewModelScope.launch { preferencesManager.setHapticFeedbackEnabled(enabled) }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            authDataStore.clearSession()
            onComplete()
        }
    }
}
