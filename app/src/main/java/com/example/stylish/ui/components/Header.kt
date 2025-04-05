package com.example.stylish.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.stylish.ui.theme.MontserratFont


@Composable
fun Header() {
    Column(
        Modifier.fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.Start

    ) {

        Text(
            "Welcome",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = MontserratFont,
        )

        Text(
            "Back!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = MontserratFont,
        )

    }
}
