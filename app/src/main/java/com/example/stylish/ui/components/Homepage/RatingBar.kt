package com.example.stylish.presentation.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun RatingBar(rating: Double) {
    Row {
        repeat(5) { index ->
            val starColor = if (index < rating.toInt()) Color(0xFFFFD700) else Color.Gray
            Icon(Icons.Filled.Star, contentDescription = "Star", tint = starColor)
        }
    }
}
