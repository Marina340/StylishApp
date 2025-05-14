package com.example.stylish.presentation.pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
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
                0 -> HomeScreen(navController , user )
                1 -> SearchScreen()
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
