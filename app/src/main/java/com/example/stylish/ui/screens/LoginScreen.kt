package com.example.stylish.screens


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
import com.example.stylish.ui.components.ButtonComponent
import com.example.stylish.ui.components.Header
import com.example.stylish.ui.components.TextFieldComponent

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


        Header()
        Spacer(modifier = Modifier.height(16.dp))


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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Forgot Password?",
            color = Color.Red,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    navController.navigate("forgot_password")
                })
        Spacer(modifier = Modifier.height(16.dp))
        ButtonComponent {
            println("Login Clicked")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Create An Account", color = Color.Red, modifier = Modifier.clickable {
            navController.navigate("register")
        })
    }
}
