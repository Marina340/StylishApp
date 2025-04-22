package com.example.stylish.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchScreen() {
    Scaffold { paddingValues ->  // ✅ Correct way
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Search Screen", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    SearchScreen()
}
