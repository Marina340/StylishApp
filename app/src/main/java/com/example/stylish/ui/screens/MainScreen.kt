package com.example.stylish.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stylish.presentation.components.BottomNavBar
import com.example.stylish.ui.screens.CheckoutScreens.Checkout
import com.example.stylish.ui.screens.ProfileScreens.ProfileScreen
import com.example.stylish.ui.screens.WishlistPage
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedScreen by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedItem = selectedScreen, onItemSelected = { selectedScreen = it })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                0 -> HomeScreen( )
                1 -> SearchScreen()
                2 -> ShoppingBagScreen()
                3 -> WishlistPage()
                4 -> SettingsScreen()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}
