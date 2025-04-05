package com.example.stylish.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RegisterScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create an Account", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = "", onValueChange = {}, placeholder = { Text("Username or Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = "", onValueChange = {}, placeholder = { Text("Password") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = "", onValueChange = {}, placeholder = { Text("Confirm Password") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Handle register */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Create Account")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Already Have an Account? Login", color = Color.Red, modifier = Modifier.clickable {
            navController.navigate("login")
        })
    }
}
