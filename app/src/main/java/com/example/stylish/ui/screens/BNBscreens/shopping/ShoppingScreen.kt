package com.example.stylish.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShoppingScreen() {
    Scaffold { paddingValues ->  // ✅ Fix paddingValues
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Shopping Screen",
                style = MaterialTheme.typography.headlineMedium // ✅ Use Material 3 style
            )
        }
    }
}

@Preview
@Composable
fun PreviewShoppingScreen() {
    ShoppingScreen()
}
