package com.example.stylish.ui.components.LoginComponents

fun validateRegistrationInput(username: String, password: String, confirmPassword: String): Pair<Boolean, String> {
    val isUsernameValid = username.matches(Regex("^[a-zA-Z0-9._-]{3,}$")) || username.contains("@gmail.com")
    val isPasswordValid = password.length > 3
    val doPasswordsMatch = password == confirmPassword

    return when {
        !isUsernameValid -> Pair(false, "Invalid username. It must be at least 3 characters or a valid email.")
        !isPasswordValid -> Pair(false, "Password must be longer than 3 characters.")
        !doPasswordsMatch -> Pair(false, "Passwords do not match.")
        else -> Pair(true, "")
    }
}
