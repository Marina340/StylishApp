package com.example.stylish.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red, // Red color for selected item
                unselectedIconColor = Color.Gray // Gray color for unselected items
            )
        )
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Shopping") },
            label = { Text("Shopping") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red, // Red color for selected item
                unselectedIconColor = Color.Gray // Gray color for unselected items
            )
        )
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) },
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            label = { Text("Search") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red, // Red color for selected item
                unselectedIconColor = Color.Gray // Gray color for unselected items
            )
        )
        NavigationBarItem(
            selected = selectedItem == 3,
            onClick = { onItemSelected(3) },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red, // Red color for selected item
                unselectedIconColor = Color.Gray // Gray color for unselected items
            )
        )
    }
}
