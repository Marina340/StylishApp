package com.example.stylish.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stylish.presentation.pages.MainScreen
import com.example.stylish.ui.screens.HomeScreen
import com.example.stylish.ui.screens.LoginScreens.ForgotPasswordScreen
import com.example.stylish.ui.screens.LoginScreens.LoginScreen
import com.example.stylish.ui.screens.LoginScreens.RegisterScreen

@Composable
fun AppNavHost(  navController: NavHostController = rememberNavController()) {

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController  ) }
        composable("register") { RegisterScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("main") { MainScreen(navController) }
        //composable("checkout") { CheckoutScreen(navController) }
    }
}
