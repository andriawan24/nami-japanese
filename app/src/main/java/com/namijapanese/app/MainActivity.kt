package com.namijapanese.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.namijapanese.core.designsystem.theme.NamiJapaneseTheme
import com.namijapanese.feature.auth.LoginScreen
import com.namijapanese.feature.auth.LoginViewModel
import com.namijapanese.feature.home.HomeScreen
import com.namijapanese.feature.kana.KanaDetailScreen
import com.namijapanese.feature.kana.KanaListScreen
import com.namijapanese.feature.progress.ProgressScreen
import com.namijapanese.feature.quiz.QuizScreen
import com.namijapanese.feature.settings.SettingsScreen
import com.namijapanese.feature.writing.WritingPracticeScreen
import com.namijapanese.core.model.KanaType
import dagger.hilt.android.AndroidEntryPoint

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Hiragana : Screen("hiragana")
    object Katakana : Screen("katakana")
    object KanaDetail : Screen("kana/{characterId}") {
        fun createRoute(characterId: String) = "kana/$characterId"
    }
    object Writing : Screen("writing/{characterId}") {
        fun createRoute(characterId: String) = "writing/$characterId"
    }
    object Quiz : Screen("quiz/{quizType}") {
        fun createRoute(quizType: String) = "quiz/$quizType"
    }
    object Progress : Screen("progress")
    object Settings : Screen("settings")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NamiJapaneseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NamiJapaneseRoot()
                }
            }
        }
    }
}

@Composable
fun NamiJapaneseRoot(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        uiState.isLoggedIn -> {
            NamiNavGraph()
        }
        else -> {
            LoginScreen(onLoginSuccess = { /* Navigation handled by LoginViewModel state */ })
        }
    }
}

@Composable
fun NamiNavGraph(
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onStartHiragana = { navController.navigate(Screen.Hiragana.route) },
                onStartKatakana = { navController.navigate(Screen.Katakana.route) },
                onStartQuiz = { navController.navigate(Screen.Quiz.createRoute("hiragana")) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Hiragana.route) {
            KanaListScreen(
                kanaType = KanaType.HIRAGANA,
                onBackClick = { navController.popBackStack() },
                onCharacterClick = { navController.navigate(Screen.KanaDetail.createRoute(it)) }
            )
        }

        composable(Screen.Katakana.route) {
            KanaListScreen(
                kanaType = KanaType.KATAKANA,
                onBackClick = { navController.popBackStack() },
                onCharacterClick = { navController.navigate(Screen.KanaDetail.createRoute(it)) }
            )
        }

        composable(
            route = Screen.KanaDetail.route,
            arguments = listOf(navArgument("characterId") { type = NavType.StringType })
        ) {
            KanaDetailScreen(
                onBackClick = { navController.popBackStack() },
                onPracticeClick = { navController.navigate(Screen.Writing.createRoute(it)) },
                onQuizClick = { navController.navigate(Screen.Quiz.createRoute("hiragana")) }
            )
        }

        composable(
            route = Screen.Writing.route,
            arguments = listOf(navArgument("characterId") { type = NavType.StringType })
        ) {
            WritingPracticeScreen(
                onBackClick = { navController.popBackStack() },
                onComplete = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(navArgument("quizType") { type = NavType.StringType })
        ) {
            QuizScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Progress.route) {
            ProgressScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onLogout = onLogout
            )
        }
    }
}
