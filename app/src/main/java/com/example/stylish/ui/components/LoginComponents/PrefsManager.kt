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
        users.removeIf { it.username == user.username || it.email == user.email }
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
        return getUserList().find { (it.username == username || it.email == username) && it.token == password }
    }

    fun updatePassword( username : String ,oldPassword: String, newPassword: String): Boolean {
        val users = getUserList().toMutableList()
        var updated = false
        for (user in users) {
            if ((user.username == username || user.email == username) && user.token == oldPassword) {
                user.token = newPassword
                updated = true
                break
            }
        }
        if (updated) {
            prefs.edit().putString("user_list", gson.toJson(users)).apply()
        }
        return updated
    }

    fun deleteUser(username: String): Boolean {
        val users = getUserList().toMutableList()
        val removed = users.removeIf { it.username == username || it.email == username }
        if (removed) {
            prefs.edit().putString("user_list", gson.toJson(users)).apply()
        }
        return removed
    }


    // PrefsManager.kt

    fun updateUserProfileImage(username: String, imageUri: String) {
        val users = getUserList().toMutableList()
        for (user in users) {
            if (user.username == username) {
                user.image = imageUri
                break
            }
        }
        prefs.edit().putString("user_list", gson.toJson(users)).apply()
    }

    fun getUserProfileImage(username: String): String? {
        return getUserList().find { it.username == username }?.image
    }

    fun getUserProfile(username: String): LoginResponse? {
        return getUserList().find { it.username == username }
    }


    fun saveUserList(userList: List<LoginResponse>) {
        val json = gson.toJson(userList)
        prefs.edit().putString("user_list", json).apply()
    }
    // ✅ حفظ حالة تسجيل الدخول
    fun setLoggedIn(user: LoginResponse) {
        saveUser(user)
        prefs.edit()
            .putBoolean("is_logged_in", true)
            .putString(CURRENT_USER_KEY, user.username)
            .apply()
    }


    // ✅ التحقق من حالة تسجيل الدخول
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }
    fun getLoggedInUsername(): String? {
        val users = getUserList()
        return if (isLoggedIn() && users.isNotEmpty()) users.last().username else null
    }

    private val CURRENT_USER_KEY = "current_logged_in_user"

    // ✅ تسجيل الخروج
    fun logout() {
        prefs.edit().putBoolean("is_logged_in", false).apply()
        prefs.edit().remove(CURRENT_USER_KEY).apply()
    }

    fun getCurrentUser(): LoginResponse? {
        val username = prefs.getString(CURRENT_USER_KEY, null)
        return if (username != null) getUserProfile(username) else null
    }
    // PrefsManager.kt
//
//    // ✅ حفظ حالة تسجيل الدخول
//    fun setLoggedIn(user: LoginResponse) {
//        saveUser(user)
//        prefs.edit()
//            .putBoolean("is_logged_in", true)
//            .putString("logged_in_username", user.username)
//            .apply()
//    }
//
//    // ✅ التحقق من حالة تسجيل الدخول
//    fun isLoggedIn(): Boolean {
//        return prefs.getBoolean("is_logged_in", false)
//    }
//    fun getLoggedInUsername(): String? {
//        val users = getUserList()
//        return if (isLoggedIn() && users.isNotEmpty()) users.last().username else null
//    }
//
//    private val CURRENT_USER_KEY = "current_logged_in_user"
//
//    // ✅ تسجيل الخروج
//    fun logout() {
//        prefs.edit().putBoolean("is_logged_in", false).apply()
//        prefs.edit().remove(CURRENT_USER_KEY).apply()
//    }
//    fun setCurrentUser(username: String) {
//        prefs.edit().putString(CURRENT_USER_KEY, username).apply()
//    }
//
//    fun getCurrentUser(): LoginResponse? {
//        val username = prefs.getString(CURRENT_USER_KEY, null)
//        return if (username != null) getUserProfile(username) else null
//    }
//
//
//
//
}

//****************
