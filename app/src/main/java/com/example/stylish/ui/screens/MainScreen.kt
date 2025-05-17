package com.example.stylish.presentation.pages
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.settingscreen.SettingScreens.SettingsScreen
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.presentation.components.BottomNavBar
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.screens.WishlistPage
@Composable
fun MainScreen(navController: NavController,prefs: PrefsManager , user :LoginResponse?) {
//    val navController = rememberNavController()

    var selectedScreen by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedItem = selectedScreen, onItemSelected = { selectedScreen = it })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                0 -> HomeScreen(navController,user)
                1 -> SearchScreen(navController)
                2 -> ShoppingBagScreen(navController)
                3 -> WishlistPage()
                4 -> SettingsScreen(navController , prefs, user)
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewMainScreen() {
//    MainScreen()
//}
