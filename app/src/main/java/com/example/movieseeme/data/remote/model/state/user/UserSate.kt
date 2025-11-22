package com.example.movieseeme.data.remote.model.state.user

import android.util.Patterns

data class UserSate(
    var sendEmail: Boolean = false,
    var forGotPassword: Boolean = false,
    var click: Boolean = false,
    val isLoading: Boolean = false,
    val code: String? = null,
    val message: String? = null,
    val success: Boolean? = false,
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val keyResetPass: String = "",
) {
    val isUserNameError get() = username.length < 8
    val isPasswordError get() = password.length < 8

    val isNewPasswordError get() = newPassword.length < 8
    val isConfirmPasswordError get() = confirmPassword != password || confirmPassword.isEmpty()

    val isConfirmChangePasswordError get() = confirmPassword != newPassword || confirmPassword.isEmpty()
    val isEmailError get() = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
}