package com.example.stylish.ui.screens.ProfileScreens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.ui.components.LoginComponents.ButtonComponent
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import androidx.navigation.NavController
import com.example.stylish.R
import com.example.stylish.ui.components.ProfileComponents.CheckoutTopBar
import com.example.stylish.ui.components.ProfileComponents.HorizontalLine
import com.example.stylish.ui.components.ProfileComponents.PasswordFieldComponent
import com.example.stylish.ui.components.ProfileComponents.ProfileBoxWithDialog
import com.example.stylish.ui.components.ProfileComponents.ProfileTopBar
import com.example.stylish.ui.components.ProfileComponents.TextFieldComponent
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontBold
import com.example.stylish.ui.theme.MontserratFontThin


@Composable
fun ProfileScreen(navController: NavController ,prefsManager: PrefsManager, user :LoginResponse?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        var email by remember { mutableStateOf(user!!.email) }
        var password by remember { mutableStateOf(user!!.token) }
        var Pincode by remember { mutableStateOf("450116") }
        var Address by remember { mutableStateOf("216 St Paul's Rd, ") }
        var City by remember { mutableStateOf("London") }
        var State by remember { mutableStateOf("N1 2LL,") }
        var Country by remember { mutableStateOf("United Kingdom") }
        ProfileTopBar({
            navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
            navController.navigate("main")}
            )

        Spacer(modifier = Modifier.height(16.dp))


        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            ProfileBoxWithDialog(navController ,prefsManager, user)
        }

        Spacer(modifier = Modifier.height(35.dp))

        Column (Modifier.padding(start = 15.dp)){
            Text(text = "Personal Details", fontSize = 25.sp ,
                fontFamily = MontserratFontBold)

            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Email Address" ,
                { email = it },
                email ,true)
            Spacer(modifier = Modifier.height(20.dp))

            PasswordFieldComponent("Password" ,
                { password = it },
                "**********" , true)

        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Change Password",
            color = DatkPink,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                    navController.navigate("changePassword" )},
            fontFamily = MontserratFontThin,
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(35.dp))
        HorizontalLine()

        Spacer(modifier = Modifier.height(35.dp))

        Column (Modifier.padding(start = 15.dp)){
            Text(text = "Business Address Details", fontSize = 25.sp ,
                fontFamily = MontserratFontBold)

            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Pincode" ,
                { Pincode = it } ,
                Pincode , false)
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Address" ,
                { Address = it } ,
                Address , false)
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("City" ,
                { City = it },
                City , false)
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("State" ,
                { State = it },
                State , false)
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Country" ,
                { Country = it },
                Country , false)

            Spacer(modifier = Modifier.height(35.dp))




        }

        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent({
            navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
            navController.navigate("main" )
        } , "Save")
    }
}
