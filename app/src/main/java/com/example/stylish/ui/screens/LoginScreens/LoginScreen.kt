package com.example.stylish.ui.screens.LoginScreens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.stylish.ui.components.LoginComponents.ButtonComponent
import com.example.stylish.ui.components.LoginComponents.Header
import com.example.stylish.ui.components.LoginComponents.TextFieldComponent
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontThin

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally ,


        ) {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .padding(50.dp)
                .border( border = BorderStroke(2.dp, Color. Blue), shape = CutCornerShape(8.dp))
        ) {}


        Header("Welcome" , "Back!")
        Spacer(modifier = Modifier.height(20.dp))


        TextFieldComponent(
            value = username,
            onValueChange = { username = it },
            placeholder = "Username or Email",
            leadingIcon = Icons.Filled.Person

        )

        Spacer(modifier = Modifier.height(10.dp))

        TextFieldComponent(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            leadingIcon =  Icons.Filled.Lock
                ,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Forgot Password?",
            color = DatkPink,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    navController.navigate("forgot_password")
                },
            fontFamily = MontserratFontThin ,
            fontWeight = FontWeight(200),
            fontSize = 15.sp)
        Spacer(modifier = Modifier.height(25.dp))
        ButtonComponent ({
            println("Login Clicked")
        }  , "Login")
        Spacer(modifier = Modifier.height(30.dp))
        val annotatedText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("Create An Account ")
            }
            withStyle(
                style = SpanStyle(
                    color = DatkPink,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Sign Up")
            }
        }

        Text(
            text = annotatedText,
            fontFamily = MontserratFontThin,
            modifier = Modifier.clickable {
                    navController.navigate("register")
                },
        )
    }
}
