package com.example.stylish.ui.screens.CheckoutScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stylish.R
import com.example.stylish.ui.components.CheckoutComponents.CheckoutTopBar
import com.example.stylish.ui.components.CheckoutComponents.HorizontalLine
import com.example.stylish.ui.components.CheckoutComponents.PasswordFieldComponent
import com.example.stylish.ui.components.CheckoutComponents.TextFieldComponent
import com.example.stylish.ui.components.LoginComponents.ButtonComponent
import com.example.stylish.ui.theme.DatkPink
import com.example.stylish.ui.theme.MontserratFontBold
import com.example.stylish.ui.theme.MontserratFontThin

@Composable
fun CheckoutScreen(/*navController: NavController*/) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Box(modifier = Modifier.height(20.dp)) {}
        CheckoutTopBar({})

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Image(
                painter = painterResource(id = R.drawable.img), // صورة أفاتار من الموارد
                contentDescription = "Profile",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.Blue, CircleShape)
                    .padding(4.dp)
                    .size(20.dp)
                , tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Column (Modifier.padding(start = 15.dp)){
            Text(text = "Personal Details", fontSize = 25.sp ,
                fontFamily = MontserratFontBold)

            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Email Address" ,"aashifa@gmail.com")
            Spacer(modifier = Modifier.height(20.dp))

            PasswordFieldComponent("Password" ,"**********")

        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Change Password",
            color = DatkPink,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { /* تغيير كلمة السر */ },
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
            TextFieldComponent("Pincode" ,"450116")
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Address" ,"216 St Paul's Rd, ")
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("City" ,"London")
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("State" ,"N1 2LL,")
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Country" ,"United Kingdom")

            Spacer(modifier = Modifier.height(35.dp))
            HorizontalLine()

            Spacer(modifier = Modifier.height(35.dp))



        }

        Column (Modifier.padding(start = 15.dp)) {
            Text(
                text = "Bank Account Details", fontSize = 25.sp,
                fontFamily = MontserratFontBold
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Bank Account Number", "204356XXXXXXX")
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("Account Holder’s Name", "Abhiraj Sisodiya")
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldComponent("IFSC Code", "SBIN00428")
        }

        ButtonComponent({} , "Save")
    }
}
