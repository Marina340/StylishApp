package com.example.stylish.data.local

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

fun sendPasswordToEmail(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    val url = "https://formspree.io/f/xanoewrg"
    val json = """
        {
            "email": "$email",
            "message": "Your temporary password is: $password"
        }
    """.trimIndent()

    val body = json.toRequestBody("application/json".toMediaTypeOrNull())
    val request = Request.Builder()
        .url(url)
        .post(body)
        .addHeader("Accept", "application/json")
        .build()

    Log.d("Formspree", "Sending email to $email with payload: $json")

    OkHttpClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Formspree", "Error: ${e.message}", e)
            onFailure()
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.i("Formspree", "Success: Email sent successfully.")
                onSuccess()
            } else {
                Log.e("Formspree", "Failed to send email. Response code: ${response.code}")
                Log.e("Formspree", "Error details: ${response.body?.string()}")
                onFailure()
            }
        }
    })
}
