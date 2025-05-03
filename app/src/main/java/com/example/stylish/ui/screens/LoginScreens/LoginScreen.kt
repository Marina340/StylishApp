package com.example.stylish.ui.screens.LoginScreens

//api username: emilys // password: emilyspass

//local username: mariammariam // password: 1234

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.stylish.data.Models.LoginRequest
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.data.Models.Domain.shared.Api_client
import com.example.stylish.ui.components.LoginComponents.ButtonComponent
import com.example.stylish.ui.components.LoginComponents.Header
import com.example.stylish.ui.components.LoginComponents.TextFieldComponent
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontThin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.biometric.BiometricManager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.ui.Alignment
import androidx.fragment.app.FragmentActivity
import com.example.stylish.ui.components.LoginComponents.authenticateWithBiometric
@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val activity = context as? FragmentActivity


    var isBiometricAvailable by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        val biometricManager = BiometricManager.from(context)
        val canAuthenticate = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
        )

        when (canAuthenticate) {
            BiometricManager.BIOMETRIC_SUCCESS -> isBiometricAvailable = true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> isBiometricAvailable = false
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> isBiometricAvailable = false
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // المعنى أن المستخدم لم يقم بتسجيل بصمة
                isBiometricAvailable = false
            }
            else -> isBiometricAvailable = false
        }
    }

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
                .fillMaxWidth()
                .height(100.dp)
        ) {

        }


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
                    navController.navigate("forgetpassword")
                },
            fontFamily = MontserratFontThin ,
            fontWeight = FontWeight(200),
            fontSize = 15.sp)
        Spacer(modifier = Modifier.height(25.dp))

        val context = LocalContext.current
        val prefs by remember { mutableStateOf(PrefsManager(context)) }
        ButtonComponent ({

            val localUser = prefs.findUser(username, password)

            if (localUser != null) {
                Toast.makeText(context, "Login Successful (Local)", Toast.LENGTH_SHORT).show()
                println("Local login successful for user: ${localUser.username}")
                navController.navigate("main")
            } else {
                val loginRequest = LoginRequest(username = username, password = password)

                Api_client.api.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse != null) {
                                prefs.saveUser(loginResponse)
                                Toast.makeText(context, "Login Successful (API)", Toast.LENGTH_SHORT).show()
                                println("API login successful for user: ${loginResponse.username}")
                                navController.navigate("main")
                            }
                        } else {
                            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                            println("Login failed with code: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context, "Login Error", Toast.LENGTH_SHORT).show()
                        println(" Login error: ${t.message}")
                    }
                })
            }

        }  , "Login")

        Spacer(modifier = Modifier.height(15.dp))
        Row() {
    if (isBiometricAvailable && activity != null) {
        Button(
            onClick = {
                authenticateWithBiometric(navController ,context, activity)
            },
            colors = ButtonDefaults.buttonColors(containerColor = DatkPink),
            modifier = Modifier.width(70.dp).height(70.dp) ,
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(0.dp)

        ) {
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = "Biometric Icon",
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )


        }
    }
}

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
