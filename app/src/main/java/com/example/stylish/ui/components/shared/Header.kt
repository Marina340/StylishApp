package com.example.stylish.ui.components.shared


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylish.ui.theme.MontserratFontBold


@Composable
fun Header(str1 : String  , str2 : String) {
    Column(
        Modifier.fillMaxWidth().padding(start = 10.dp),
        horizontalAlignment = Alignment.Start

    ) {

        Text(
            str1,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = MontserratFontBold,
        )

        Text(
            str2,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = MontserratFontBold,
        )

    }
}
