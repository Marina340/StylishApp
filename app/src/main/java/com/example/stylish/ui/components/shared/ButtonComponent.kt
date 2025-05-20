package com.example.stylish.ui.components.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontBold

@Composable
fun ButtonComponent(onClick: () -> Unit , submitString: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor =DatkPink
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = submitString,
            fontSize = 22.sp,
            color = Color.White,
            fontFamily = MontserratFontBold ,
        )
    }
}
