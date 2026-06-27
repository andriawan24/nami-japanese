package com.namijapanese.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_session")

data class LocalUserSession(
    val isLoggedIn: Boolean = false,
    val googleUserId: String? = null,
    val email: String? = null,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val lastLoginAt: Long? = null
)

@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val GOOGLE_USER_ID = stringPreferencesKey("google_user_id")
        val EMAIL = stringPreferencesKey("email")
        val DISPLAY_NAME = stringPreferencesKey("display_name")
        val PHOTO_URL = stringPreferencesKey("photo_url")
        val LAST_LOGIN_AT = longPreferencesKey("last_login_at")
    }

    val userSessionFlow: Flow<LocalUserSession> = context.authDataStore.data.map { prefs ->
        LocalUserSession(
            isLoggedIn = prefs[Keys.IS_LOGGED_IN] ?: false,
            googleUserId = prefs[Keys.GOOGLE_USER_ID],
            email = prefs[Keys.EMAIL],
            displayName = prefs[Keys.DISPLAY_NAME],
            photoUrl = prefs[Keys.PHOTO_URL],
            lastLoginAt = prefs[Keys.LAST_LOGIN_AT]
        )
    }

    suspend fun saveGoogleSession(
        googleUserId: String,
        email: String,
        displayName: String?,
        photoUrl: String?
    ) {
        context.authDataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = true
            prefs[Keys.GOOGLE_USER_ID] = googleUserId
            prefs[Keys.EMAIL] = email
            prefs[Keys.DISPLAY_NAME] = displayName ?: ""
            prefs[Keys.PHOTO_URL] = photoUrl ?: ""
            prefs[Keys.LAST_LOGIN_AT] = System.currentTimeMillis()
        }
    }

    suspend fun clearSession() {
        context.authDataStore.edit { it.clear() }
    }
}
