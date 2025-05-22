package com.example.stylish.ui.components.ProfileComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.example.stylish.R
import com.example.stylish.data.Models.LoginResponse
import com.example.stylish.ui.components.LoginComponents.PrefsManager
import com.example.stylish.ui.theme.DatkPink

// CustomDialog.kt

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onSelectImage: (String) -> Unit,
    onTakeImage: (String) -> Unit
) {
    val context = LocalContext.current
    val takeImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = saveImageToInternalStorage(context = context , bitmap)
            if (uri != null) onTakeImage(uri.toString())
        }
    }

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onSelectImage(it.toString()) }
    }

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

                Button(
                    onClick = { selectImageLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DatkPink),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Select Image")
                }

                Button(
                    onClick = { takeImageLauncher.launch(null) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DatkPink),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Take Image")
                }
            }
        }
    }
}

@Composable
fun ProfileBoxWithDialog(navController: NavController  , prefsManager: PrefsManager, user: LoginResponse?) {
    var showDialog by remember { mutableStateOf(false) }
    val profileImageUri = user?.image.takeIf { !it.isNullOrEmpty() } ?: "android.resource://${LocalContext.current.packageName}/${R.drawable.img}"


    Box(
        modifier = Modifier
            .size(120.dp)
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberImagePainter(
                data = profileImageUri,
                builder = {
                    error(R.drawable.img) // صورة افتراضية عند حدوث خطأ
                }
            ),
            contentDescription = "Profile",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-8).dp, y = (-8).dp)
                .clip(CircleShape)
                .background(Color(0xFF2196F3)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }

    if (showDialog) {
        CustomDialog(
            onDismiss = { showDialog = false },
            onSelectImage = { uri ->
                prefsManager.updateUserProfileImage(user!!.username, uri)
                // جلب بيانات المستخدم المحدثة
                val updatedUser = prefsManager.getUserProfile(user.username)

                // إرسال البيانات المحدثة
                if (updatedUser != null) {
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", updatedUser)
                }
                showDialog = false

                navController.navigate("main")
            },
            onTakeImage = { uri ->
                prefsManager.updateUserProfileImage(user!!.username, uri)

                // جلب بيانات المستخدم المحدثة
                val updatedUser = prefsManager.getUserProfile(user.username)

                // إرسال البيانات المحدثة
                if (updatedUser != null) {
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", updatedUser)
                }
                showDialog = false

                navController.navigate("main")
            }
        )
    }
}
