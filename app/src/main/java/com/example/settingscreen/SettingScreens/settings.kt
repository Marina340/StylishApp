package com.example.settingscreen.SettingScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.settingscreen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Settings",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        content = { innerPadding ->
            SettingsContent(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    )
}

@Composable
fun SettingsContent(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        SettingItem(
            title = "Notification Settings",
            iconRes = R.drawable.ic_notification,
            onClick = { navController.navigate("notification") }
        )
        Divider(color = Color.LightGray, thickness = 0.5.dp)

        SettingItem(
            title = "Password Manager",
            iconRes = R.drawable.ic_password,
            onClick = { navController.navigate("password") }
        )
        Divider(color = Color.LightGray, thickness = 0.5.dp)

        SettingItem(
            title = "Terms Of Use",
            iconRes = R.drawable.ic_terms_of_use,
            //onClick = { navController.navigate("notification") }
        )
        Divider(color = Color.LightGray, thickness = 0.5.dp)

        SettingItem(
            title = "Delete Account",
            iconRes = R.drawable.ic_delete,
            textColor = Color.Red
        )
        Divider(color = Color.LightGray, thickness = 0.5.dp)

        SettingItem(
            title = "Log Out",
            iconRes = R.drawable.ic_logout,
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    iconRes: Int,
    textColor: Color = Color.Black,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF6B4F3B)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Go to $title",
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
    }
}
