package com.example.settingscreen.SettingScreens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.stylish.R
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.components.ProfileComponents.saveImageToInternalStorage
import com.example.stylish.ui.theme.DatkPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController , prefsManager: PrefsManager, user: LoginResponse?) {
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
                , prefsManager, user
            )
        }
    )
}

@Composable
fun SettingsContent(modifier: Modifier = Modifier, navController: NavController, prefsManager: PrefsManager, user: LoginResponse?) {
    var showDeleteDialog by remember { mutableStateOf(false) }
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
            textColor = Color.Red,
            onClick = {showDeleteDialog = true }
        )
        Divider(color = Color.LightGray, thickness = 0.5.dp)
        if (showDeleteDialog) {
            confirmBox(
                navController = navController,
                prefsManager = prefsManager,
                user = user,
                onDismiss = { showDeleteDialog = false }
            )
        }
        SettingItem(
            title = "Log Out",
            iconRes = R.drawable.ic_logout,
            onClick = { navController.navigate("login")}
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


@Composable
fun DeleteAccountDialog(
    onDismiss: () -> Unit,
    onAgree: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { onDismiss() }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

//                Button(
//                    onClick = { onAgree() },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(containerColor = DatkPink),
//                    shape = RoundedCornerShape(6.dp)
//                ) {
//                    Text("Agree")
//                }

                Text(
                    text = "Are you sure you want to permanently delete your account?",
                    fontSize = 14.sp,
                    color = DatkPink,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 7.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DatkPink),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onAgree() },
                    modifier = Modifier.fillMaxWidth(.5f),
                    colors = ButtonDefaults.buttonColors(containerColor = DatkPink),
                    shape = RoundedCornerShape(6.dp)
                    ) {
                        Text("Delete")
                    }
                }


            }
        }
    }
}

@Composable
fun confirmBox(navController: NavController,
               prefsManager: PrefsManager, user: LoginResponse?
, onDismiss: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        DeleteAccountDialog(
            onDismiss = { onDismiss() },
            onAgree = {
                if (user != null) {
                    prefsManager.deleteUser(user.username)
                }
                showDialog = false

                navController.navigate("login")
            }
        )
    }
}
