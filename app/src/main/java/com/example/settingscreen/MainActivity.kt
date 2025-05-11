package com.example.settingscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.settingscreen.SettingScreens.NotificationScreen
import com.example.settingscreen.SettingScreens.PasswordScreen
import com.example.settingscreen.SettingScreens.SettingsScreen
import com.example.settingscreen.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingScreenTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "settings") {
                        composable("settings") { SettingsScreen(navController) }
                        composable("notification") { NotificationScreen(navController) }
                        composable("password") { PasswordScreen(navController) }
                    }
                }
            }
        }
    }
}
