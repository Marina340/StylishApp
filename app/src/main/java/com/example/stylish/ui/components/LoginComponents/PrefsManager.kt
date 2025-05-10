package com.example.stylish.ui.components.LoginComponents

import android.content.Context
import com.example.stylish.data.Models.LoginResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(user: LoginResponse) {
        val users = getUserList().toMutableList()
        users.add(user)
        val json = gson.toJson(users)
        prefs.edit().putString("user_list", json).apply()
    }

    fun getUserList(): List<LoginResponse> {
        val json = prefs.getString("user_list", null) ?: return emptyList()
        val type = object : TypeToken<List<LoginResponse>>() {}.type
        return gson.fromJson(json, type)
    }

    fun findUser(username: String, password: String): LoginResponse? {
        return getUserList().find { it.username == username && it.token == password }
    }
    fun saveUserList(userList: List<LoginResponse>) {
        val json = gson.toJson(userList)
        prefs.edit().putString("user_list", json).apply()
    }

}