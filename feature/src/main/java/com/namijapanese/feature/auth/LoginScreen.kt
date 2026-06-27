package com.namijapanese.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.namijapanese.core.R
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Base64

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val credentialManager = remember { CredentialManager.create(context) }

    val serverClientId = stringResource(R.string.google_web_client_id)

    val googleIdOption = remember(serverClientId) {
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .setServerClientId(serverClientId)
            .build()
    }

    val credentialRequest = remember {
        GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    fun handleCredentialResponse(response: GetCredentialResponse) {
        val credential = response.credential
        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                val payload = decodeIdTokenPayload(idToken)
                if (payload != null) {
                    val resolvedName = googleIdTokenCredential.displayName
                        ?: payload.name
                        ?: payload.email.substringBefore("@")
                    val resolvedPhoto = googleIdTokenCredential.profilePictureUri?.toString()
                        ?: payload.picture
                    viewModel.onGoogleLoginSuccess(
                        googleUserId = payload.sub,
                        email = payload.email,
                        displayName = resolvedName,
                        photoUrl = resolvedPhoto
                    )
                } else {
                    viewModel.onGoogleLoginError("Failed to parse Google account data.")
                }
            } catch (e: Exception) {
                viewModel.onGoogleLoginError("Login failed: ${e.localizedMessage}")
            }
        } else {
            viewModel.onGoogleLoginError("Unexpected credential type.")
        }
    }

    fun launchGoogleLogin() {
        viewModel.clearError()
        scope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = credentialRequest,
                    context = context
                )
                handleCredentialResponse(result)
            } catch (e: GetCredentialCancellationException) {
                viewModel.onGoogleLoginError("Login was cancelled.")
            } catch (e: Exception) {
                viewModel.onGoogleLoginError("Login failed: ${e.localizedMessage}")
            }
        }
    }

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "波",
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nami Japanese",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Learn Japanese, One Stroke at a Time.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (uiState.isLoading && uiState.errorMessage == null) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    com.namijapanese.core.designsystem.component.NamiPrimaryButton(
                        text = "Continue with Google",
                        onClick = { launchGoogleLogin() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                uiState.errorMessage?.let { error ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Your learning progress is stored locally on this device.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

private data class GoogleIdPayload(
    val sub: String,
    val email: String,
    val name: String?,
    val picture: String?
)

private fun decodeIdTokenPayload(idToken: String): GoogleIdPayload? {
    return try {
        val parts = idToken.split(".")
        if (parts.size < 2) return null
        val payload = parts[1]
        val decoded = Base64.getUrlDecoder().decode(payload)
        val json = JSONObject(String(decoded))
        GoogleIdPayload(
            sub = json.optString("sub", ""),
            email = json.optString("email", ""),
            name = json.optString("name", null),
            picture = json.optString("picture", null)
        )
    } catch (e: Exception) {
        null
    }
}
