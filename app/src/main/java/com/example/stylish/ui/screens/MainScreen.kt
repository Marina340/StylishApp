package com.example.stylish.presentation.pages

import HomeScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.stylish.presentation.components.BottomNavBar
import com.example.stylish.ui.screens.CheckoutScreens.Checkout
import com.example.stylish.ui.screens.ProfileScreens.ProfileScreen

@Composable
fun MainScreen(navController: NavController) {
    var selectedScreen by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedItem = selectedScreen, onItemSelected = { selectedScreen = it })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                0 -> HomeScreen(navController)
                1 -> ShoppingBagScreen()
                2 -> SearchScreen()
                3 ->ProfileScreen()
            }
        }
    }
}

