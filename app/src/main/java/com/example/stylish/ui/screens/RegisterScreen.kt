package com.example.stylish.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stylish.ui.components.ButtonComponent
import com.example.stylish.ui.components.Header
import com.example.stylish.ui.components.TextFieldComponent
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontThin

@Composable
fun RegisterScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(50.dp)
                .border( border = BorderStroke(2.dp, Color. Blue), shape = CutCornerShape(8.dp))
        ) {}


        Header("Create an" , "Account")

        Spacer(modifier = Modifier.height(25.dp))
        TextFieldComponent(
            value = username,
            onValueChange = { username = it },
            placeholder = "Username or Email",
            leadingIcon = Icons.Filled.Person

        )

        Spacer(modifier = Modifier.height(12.dp))

        TextFieldComponent(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            leadingIcon =  Icons.Filled.Lock
            ,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextFieldComponent(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "ConfirmPassword",
            leadingIcon =  Icons.Filled.Lock
            ,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        val annotatedText1 = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("By clicking the ")
            }
            withStyle(
                style = SpanStyle(
                    color = DatkPink,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Register")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append(" button, you agree to the public offer")
            }
        }

        Text(
            text = annotatedText1,
            fontFamily = MontserratFontThin,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(25.dp))

        ButtonComponent ({
            println("Login Clicked")
        }, "Create Account")
        Spacer(modifier = Modifier.height(30.dp))

        val annotatedText2 = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("I Already Have an Account ")
            }
            withStyle(
                style = SpanStyle(
                    color = DatkPink,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Login")
            }
        }

        Text(
            text = annotatedText2,
            fontFamily = MontserratFontThin,
            modifier = Modifier.clickable {
                navController.navigate("login")
            },
            )
    }
}
