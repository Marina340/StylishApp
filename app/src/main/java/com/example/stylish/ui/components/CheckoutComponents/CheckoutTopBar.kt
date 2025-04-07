package com.example.stylish.ui.components.CheckoutComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckoutTopBar(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(56.dp)
    ) {
        // السهم
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.Companion.align(Alignment.Companion.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                Modifier.Companion.size(35.dp)
            )
        }


        Text(
            text = "Checkout",
            modifier = Modifier.Companion.align(Alignment.Companion.Center),
            fontSize = 20.sp,
            fontWeight = FontWeight.Companion.Bold
        )
    }
}