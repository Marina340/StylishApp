package com.example.stylish.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylish.R
import com.example.stylish.presentation.pages.SearchScreen
import com.example.stylish.ui.theme.DatkPink

@Composable
fun BottomNavBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
            .height(100.dp)
    )
    {
        // Navigation Bar (matches parent Box size)
        NavigationBar(
            modifier = Modifier.matchParentSize(),
            containerColor = Color.White

        ) {
            // Left side items (Home, Search)
            NavigationBarItem(
                selected = selectedItem == 0,
                onClick = { onItemSelected(0) },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DatkPink,
                    unselectedIconColor = Color.Gray
                )
            )
            NavigationBarItem(
                selected = selectedItem == 1,
                onClick = { onItemSelected(1) },
                icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                label = { Text("Search") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DatkPink,
                    unselectedIconColor = Color.Gray
                )
            )

            // Empty spacer (centered)
            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = { Spacer(modifier = Modifier.size(24.dp)) },
                label = { Spacer(modifier = Modifier.size(0.dp)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White
                )
            )

            // Right side items (Wishlist, Settings)
            NavigationBarItem(
                selected = selectedItem == 3,
                onClick = { onItemSelected(3) },
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Wishlist") },
                label = { Text("Wishlist") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DatkPink,
                    unselectedIconColor = Color.Gray
                )
            )
            NavigationBarItem(
                selected = selectedItem == 4,
                onClick = { onItemSelected(4) },
                icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                label = { Text("Settings") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DatkPink,
                    unselectedIconColor = Color.Gray
                )
            )
        }

        // Floating Shopping Cart Button
        FloatingActionButton(
            onClick = { onItemSelected(2) },
            modifier = Modifier
                .size(60.dp) // Standard FAB size, ensures circular shape
                .align(Alignment.BottomCenter)
                .offset(y = (-40).dp), // Half above the navigation bar
            containerColor = if (selectedItem == 2) DatkPink else Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Icon(
               Icons.Filled.ShoppingCart,
                contentDescription = "Cart",
                tint = if (selectedItem == 2) Color.White else DatkPink,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    BottomNavBar(
        selectedItem = 0,
        onItemSelected = {}
    )
}
