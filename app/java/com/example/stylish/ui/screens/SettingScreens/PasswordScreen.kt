package com.example.settingscreen.SettingScreens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stylish.R
import com.example.stylish.ui.theme.pinkColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(navController: NavController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Password Manager", fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.custom_back_arrow),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            PasswordField("Current Password")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Forgot Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { /* Handle forgot password */ },
                color = pinkColor,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
            Spacer(modifier = Modifier.height(24.dp))
            PasswordField("New Password")
            Spacer(modifier = Modifier.height(16.dp))
            PasswordField("Confirm New Password")
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* Handle change password */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = pinkColor),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Change Password", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(label: String) {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(label) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier.size(36.dp) // Optional: smaller clickable area if needed
            ) {
                Icon(
                    painter = painterResource(
                        id = if (passwordVisible) R.drawable.ic_eye else R.drawable.ic_eye_off
                    ),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    modifier = Modifier.size(30.dp) // 👈 Sets icon size
                )
            }
        }
        ,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = pinkColor,
            unfocusedBorderColor = Color.Black.copy(alpha = 0.5f),
            focusedLabelColor = pinkColor,
            unfocusedLabelColor = Color.Black,
            cursorColor = pinkColor,
        )

    )
}


