package com.example.stylish.data.Models.Domain.shared

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api_client {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: loginPoint by lazy {
        retrofit.create(loginPoint::class.java)
    }
}