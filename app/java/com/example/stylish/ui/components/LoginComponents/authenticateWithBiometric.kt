package com.example.stylish.ui.components.LoginComponents

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController


fun authenticateWithBiometric(navController: NavController ,context: Context, activity: FragmentActivity) {
    val executor = ContextCompat.getMainExecutor(context)

    // إنشاء الـ BiometricPrompt باستخدام activity
    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(context, "تم التحقق بنجاح!", Toast.LENGTH_SHORT).show()
                navController.navigate("main")
                // هنا يمكن إضافة التوجيه إلى الصفحة التالية إذا كنت ترغب
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(context, "فشل التحقق بالبصمة", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(context, "خطأ: $errString", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // إنشاء الـ PromptInfo
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Login")
        .setSubtitle("استخدم بصمة اليد أو الوجه")
        .setDescription("استخدم بصمة الإصبع أو الوجه للتحقق")
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()

    // تنفيذ عملية التحقق بالبصمة
    biometricPrompt.authenticate(promptInfo)
}
