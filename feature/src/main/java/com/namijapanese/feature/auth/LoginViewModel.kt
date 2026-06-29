package com.namijapanese.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namijapanese.core.data.repository.ProgressMigrationHelper
import com.namijapanese.core.datastore.AuthDataStore
import com.namijapanese.core.datastore.LocalUserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val guestName: String = "",
    val guestNameError: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val progressMigrationHelper: ProgressMigrationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authDataStore.userSessionFlow.collect { session ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = session.isLoggedIn
                    )
                }
            }
        }
    }

    fun onGoogleLoginSuccess(
        googleUserId: String,
        email: String,
        displayName: String?,
        photoUrl: String?
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            authDataStore.saveGoogleSession(
                googleUserId = googleUserId,
                email = email,
                displayName = displayName,
                photoUrl = photoUrl
            )
            progressMigrationHelper.migrateLegacyProgressIfNeeded()
        }
    }

    fun onGoogleLoginError(message: String) {
        _uiState.update { it.copy(errorMessage = message) }
    }

    fun updateGuestName(name: String) {
        _uiState.update { it.copy(guestName = name, guestNameError = null) }
    }

    fun onGuestLogin() {
        val name = _uiState.value.guestName.trim()

        // Validation
        when {
            name.isEmpty() -> {
                _uiState.update { it.copy(guestNameError = "Please enter your name") }
                return
            }
            name.length < 2 -> {
                _uiState.update { it.copy(guestNameError = "Name must be at least 2 characters") }
                return
            }
            name.length > 30 -> {
                _uiState.update { it.copy(guestNameError = "Name must be 30 characters or less") }
                return
            }
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, guestNameError = null) }
            authDataStore.saveGuestSession(displayName = name)
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
