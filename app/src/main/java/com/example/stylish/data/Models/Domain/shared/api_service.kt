package com.example.stylish.data.Models.Domain.shared

import com.example.stylish.data.Models.LoginRequest
import com.example.stylish.data.Models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface loginPoint {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}