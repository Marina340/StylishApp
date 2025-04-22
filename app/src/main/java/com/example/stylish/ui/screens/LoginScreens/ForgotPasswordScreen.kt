package com.example.stylish.ui.screens.LoginScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stylish.ui.components.LoginComponents.ButtonComponent
import com.example.stylish.ui.components.LoginComponents.Header
import com.example.stylish.ui.components.LoginComponents.TextFieldComponent
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontThin

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var email by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(50.dp)
                .border( border = BorderStroke(2.dp, Color. Blue), shape = CutCornerShape(8.dp))
        ) {}
        Header("Forgot" , "password?")
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldComponent(
            value = email,
            onValueChange = { email = it },
            placeholder = "Enter your email address",
            leadingIcon = Icons.Filled.Mail

        )

        Spacer(modifier = Modifier.height(12.dp))
        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = DatkPink,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("* ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("We will send you a message to set or reset your new password")
            }
        }

        Text(
            text = annotatedText,
            fontFamily = MontserratFontThin,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        ButtonComponent ({
            println("Login Clicked")
        }  , "Submit")
    }
}
