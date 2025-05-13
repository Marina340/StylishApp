package com.example.stylish.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.stylish.presentation.components.BottomNavBar
import com.example.stylish.ui.screens.WishlistPage
@Composable
fun MainScreen(navController: NavController) {
//    val navController = rememberNavController()
    var selectedScreen by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedItem = selectedScreen, onItemSelected = { selectedScreen = it })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                0 -> HomeScreen(navController)
                1 -> SearchScreen(navController)
                2 -> ShoppingBagScreen()
                3 -> WishlistPage()
                4 -> SettingsScreen()
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewMainScreen() {
//    MainScreen()
//}
