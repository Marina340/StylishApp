package com.example.stylish.ui.screens.ProfileScreens

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontThin
import android.widget.Toast
import com.example.stylish.domain.shared.LoginResponse


@Composable
fun ChangePassword(navController: NavController ,prefs: PrefsManager , user : LoginResponse?) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var newPassword by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(50.dp)
                .border( border = BorderStroke(2.dp, Color. Blue), shape = CutCornerShape(8.dp))
        ) {}


        Header("Change" , "Password")

        Spacer(modifier = Modifier.height(25.dp))
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
        Spacer(modifier = Modifier.height(12.dp))

        TextFieldComponent(
            value = newPassword,
            onValueChange = { newPassword = it },
            placeholder = "New Password",
            leadingIcon =  Icons.Filled.Lock
            ,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        val annotatedText1 = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("By changing your password, you confirm that you have the right to make this change and agree to our ")
            }
            withStyle(
                style = SpanStyle(
                    color = DatkPink,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Privacy Policy")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append(" and ")
            }
            withStyle(
                style = SpanStyle(
                    color = DatkPink,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Terms of Service")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append(".")
            }
        }


        Text(
            text = annotatedText1,
            fontFamily = MontserratFontThin,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(25.dp))

        val context = LocalContext.current

        ButtonComponent({
            if (prefs.updatePassword(user!!.username,password ,newPassword )){
            Toast.makeText(context, "Update Password", Toast.LENGTH_SHORT).show()
                navController.navigate("login" )
}
            else {Toast.makeText(context, "Something Error !!", Toast.LENGTH_SHORT).show()}
        }, "Change")

        Spacer(modifier = Modifier.height(30.dp))

    }
}
