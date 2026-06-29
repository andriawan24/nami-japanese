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
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_session")

enum class AuthType {
    GOOGLE,
    GUEST
}

data class LocalUserSession(
    val isLoggedIn: Boolean = false,
    val authType: AuthType? = null,
    val userId: String? = null,
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val lastLoginAt: Long? = null
) {
    val isGuest: Boolean get() = authType == AuthType.GUEST
    val isGoogle: Boolean get() = authType == AuthType.GOOGLE

    // Backward compatibility
    val googleUserId: String? get() = if (authType == AuthType.GOOGLE) userId else null
}

@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val AUTH_TYPE = stringPreferencesKey("auth_type")
        val USER_ID = stringPreferencesKey("user_id")
        val GOOGLE_USER_ID = stringPreferencesKey("google_user_id")
        val EMAIL = stringPreferencesKey("email")
        val DISPLAY_NAME = stringPreferencesKey("display_name")
        val PHOTO_URL = stringPreferencesKey("photo_url")
        val LAST_LOGIN_AT = longPreferencesKey("last_login_at")
        val HAS_MIGRATED_LEGACY_PROGRESS = booleanPreferencesKey("has_migrated_legacy_progress")
    }

    val userSessionFlow: Flow<LocalUserSession> = context.authDataStore.data.map { prefs ->
        val isLoggedIn = prefs[Keys.IS_LOGGED_IN] ?: false
        val authTypeStr = prefs[Keys.AUTH_TYPE]
        val authType = when (authTypeStr) {
            "GOOGLE" -> AuthType.GOOGLE
            "GUEST" -> AuthType.GUEST
            else -> if (isLoggedIn) {
                // Backward compatibility: if logged in but no authType, assume Google
                if (prefs[Keys.GOOGLE_USER_ID] != null) AuthType.GOOGLE else AuthType.GUEST
            } else null
        }

        val userId = prefs[Keys.USER_ID]
            ?: prefs[Keys.GOOGLE_USER_ID] // Backward compatibility

        LocalUserSession(
            isLoggedIn = isLoggedIn,
            authType = authType,
            userId = userId,
            displayName = prefs[Keys.DISPLAY_NAME],
            email = prefs[Keys.EMAIL],
            photoUrl = prefs[Keys.PHOTO_URL],
            lastLoginAt = prefs[Keys.LAST_LOGIN_AT]
        )
    }

    val hasMigratedLegacyProgress: Flow<Boolean> = context.authDataStore.data.map { prefs ->
        prefs[Keys.HAS_MIGRATED_LEGACY_PROGRESS] ?: false
    }

    suspend fun setHasMigratedLegacyProgress(migrated: Boolean) {
        context.authDataStore.edit { prefs ->
            prefs[Keys.HAS_MIGRATED_LEGACY_PROGRESS] = migrated
        }
    }

    suspend fun saveGoogleSession(
        googleUserId: String,
        email: String,
        displayName: String?,
        photoUrl: String?
    ) {
        context.authDataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = true
            prefs[Keys.AUTH_TYPE] = "GOOGLE"
            prefs[Keys.USER_ID] = googleUserId
            prefs[Keys.GOOGLE_USER_ID] = googleUserId
            prefs[Keys.EMAIL] = email
            prefs[Keys.DISPLAY_NAME] = displayName ?: ""
            prefs[Keys.PHOTO_URL] = photoUrl ?: ""
            prefs[Keys.LAST_LOGIN_AT] = System.currentTimeMillis()
        }
    }

    suspend fun saveGuestSession(displayName: String) {
        val trimmedName = displayName.trim()
        context.authDataStore.edit { prefs ->
            // Reuse existing guestId if available, otherwise generate new one
            val existingGuestId = prefs[Keys.USER_ID]
            val guestId = if (existingGuestId != null && existingGuestId.startsWith("guest_")) {
                existingGuestId
            } else {
                "guest_${UUID.randomUUID()}"
            }

            prefs[Keys.IS_LOGGED_IN] = true
            prefs[Keys.AUTH_TYPE] = "GUEST"
            prefs[Keys.USER_ID] = guestId
            prefs[Keys.DISPLAY_NAME] = trimmedName
            prefs.remove(Keys.EMAIL)
            prefs.remove(Keys.PHOTO_URL)
            prefs[Keys.LAST_LOGIN_AT] = System.currentTimeMillis()
        }
    }

    suspend fun clearSession() {
        context.authDataStore.edit { it.clear() }
    }
}
