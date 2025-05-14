package com.example.stylish.ui.components
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.stylish.OnboardingScreen
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.data.isOnboardingCompleted
import com.example.stylish.data.setOnboardingCompleted
import com.example.stylish.presentation.pages.MainScreen
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.screens.LoginScreens.ForgotPasswordScreen
import com.example.stylish.ui.screens.LoginScreens.LoginScreen
import com.example.stylish.ui.screens.LoginScreens.RegisterScreen
import com.example.stylish.ui.screens.ProfileScreens.ChangePassword
import com.example.stylish.ui.screens.ProfileScreens.ProfileScreen
import kotlinx.coroutines.launch
@Composable
fun AppNavigation(context: Context = LocalContext.current  ) {
    val navController = rememberNavController()
    var prefsManager = remember { PrefsManager(context) }
    var startDestination by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Check onboarding flag
    LaunchedEffect(Unit) {
        val completed = isOnboardingCompleted(context)
        startDestination = if (completed) "login" else "onboarding"
    }

    if (startDestination != null) {
        NavHost(navController, startDestination!!) {
            composable("onboarding") {
                OnboardingScreen(
                    onFinish = {
                        // ✅ Launch a coroutine properly
                        coroutineScope.launch {
                            setOnboardingCompleted(context)
                            navController.navigate("login") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    }
                )
            }
            composable("login") {
                LoginScreen(navController, prefsManager)
            }
            composable("forgetpassword") {
                ForgotPasswordScreen(navController, prefsManager)
            }
            composable("register") {
                RegisterScreen(navController, prefsManager)
            }
            composable("main") { backStackEntry ->
                var user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>("user")
                MainScreen(navController, prefsManager, user)
            }
            composable("profile") { backStackEntry ->
                var user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>("user")
                ProfileScreen(navController, prefsManager, user)

            }
            composable("changePassword") { backStackEntry ->
                val user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>("user")
                ChangePassword(navController, prefsManager, user)
            }
        }
    }
}