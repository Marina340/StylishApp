package com.example.stylish.ui.screens.LoginScreens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stylish.R
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.data.Models.models.AddressInfo
import com.example.stylish.ui.components.LoginComponents.ButtonComponent
import com.example.stylish.ui.components.LoginComponents.Header
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.components.LoginComponents.TextFieldComponent
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontThin


@Composable
fun RegisterScreen(navController: NavController, prefs: PrefsManager) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(50.dp)
                .border(border = BorderStroke(2.dp, Color.Blue), shape = CutCornerShape(8.dp))
        ) {}

        Header("Create an", "Account")

        Spacer(modifier = Modifier.height(25.dp))

        // Email field with validation
        Column {
            TextFieldComponent(
                value = email,
                onValueChange = {
                    email = it
                    emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                        "Invalid email format"
                    } else ""
                },
                placeholder = "Email Address",
                leadingIcon = Icons.Filled.Person
            )
            if (emailError.isNotEmpty()) {
                Text(text = emailError, color = Color.Red, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            TextFieldComponent(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = if (it.length < 6) "Password too short" else ""
                },
                placeholder = "Password",
                leadingIcon = Icons.Filled.Lock,
                isPassword = true
            )
            if (passwordError.isNotEmpty()) {
                Text(text = passwordError, color = Color.Red, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            TextFieldComponent(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError =
                        if (it != password) "Passwords do not match" else ""
                },
                placeholder = "Confirm Password",
                leadingIcon = Icons.Filled.Lock,
                isPassword = true
            )
            if (confirmPasswordError.isNotEmpty()) {
                Text(text = confirmPasswordError, color = Color.Red, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append("By clicking the ")
                }
                withStyle(style = SpanStyle(color = DatkPink, fontWeight = FontWeight.Bold)) {
                    append("Register")
                }
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append(" button, you agree to the public offer")
                }
            },
            fontFamily = MontserratFontThin,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        ButtonComponent(onClick = {
            // Reset previous error states
            emailError = ""
            passwordError = ""
            confirmPasswordError = ""

            val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val isValidPassword = password.length >= 6
            val passwordsMatch = password == confirmPassword

            if (!isValidEmail) {
                emailError = "Invalid email address"
            }
            if (!isValidPassword) {
                passwordError = "Password must be at least 6 characters"
            }
            if (!passwordsMatch) {
                confirmPasswordError = "Passwords do not match"
            }

            if (isValidEmail && isValidPassword && passwordsMatch) {
                val user = LoginResponse(
                    id = (1..1000).random(),
                    username = email.substringBefore("@"),
                    email = email,
                    firstName = "",
                    lastName = "",
                    gender = "male",
                    image = R.drawable.img.toString(),
                    token = password,
                    address = AddressInfo(
                        pincode = "450116",
                        address = "216 St Paul's Rd",
                        city = "London",
                        state = "N1 2LL",
                        country = "United Kingdom"
                    )
                )

                prefs.saveUser(user)
                Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show()
                navController.navigate("login")
            } else {
                Toast.makeText(context, "Please correct the errors", Toast.LENGTH_SHORT).show()
            }
        }, submitString = "Create Account")

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = buildAnnotatedString {
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
            },
            fontFamily = MontserratFontThin,
            modifier = Modifier.clickable {
                navController.navigate("login")
            },
        )
    }
}
