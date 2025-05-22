package com.example.stylish.ui.components.CheckoutComponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylish.ui.theme.MontserratFontThin

@Composable
fun TextFieldComponent (lable :String , placeholderText : String){

    Text(text = lable, fontSize = 18.sp ,
        fontFamily = MontserratFontThin)
    Spacer(modifier = Modifier.height(10.dp) )
    OutlinedTextField(
        value = placeholderText,
        onValueChange = {},
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
        ,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(

//            containerColor  = Color(0xFFF5F5F5),
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray
        )
    )

}

@Composable
fun PasswordFieldComponent (lable :String , placeholderText : String) {

    Text(text = lable, fontSize = 18.sp,
        fontFamily = MontserratFontThin)
    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        value = placeholderText,
        onValueChange = {},
        visualTransformation = PasswordVisualTransformation(),
        readOnly = true
        , modifier = Modifier
            .fillMaxWidth()
        ,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(

//            containerColor  = Color(0xFFF5F5F5),
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray
        )
    )
}