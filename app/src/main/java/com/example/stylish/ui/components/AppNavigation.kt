package com.example.stylish.ui.components

import HomeScreen
import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.stylish.OnboardingScreen
import com.example.stylish.data.isOnboardingCompleted
import com.example.stylish.data.setOnboardingCompleted
import com.example.stylish.presentation.pages.MainScreen
import com.example.stylish.ui.screens.LoginScreens.ForgotPasswordScreen
import com.example.stylish.ui.screens.LoginScreens.LoginScreen
import com.example.stylish.ui.screens.LoginScreens.RegisterScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@Composable
fun AppNavigation(context: Context = LocalContext.current) {
    val navController = rememberNavController()
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
                LoginScreen(navController)
            }
            composable("forgetpassword") {
                ForgotPasswordScreen(navController)
            }
            composable("register") {
                RegisterScreen(navController)
            }
            composable("home") {
                MainScreen(navController)
            }
            composable("main") {
                MainScreen(navController)
            }
        }
    } else {
        // Show loading while checking onboarding flag
//        androidx.compose.material3.CircularProgressIndicator()
    }
}
