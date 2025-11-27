package com.example.movieseeme.data.remote.model.request.auth.forgot_password

data class ResetPassRequest(
    private val token: String,
    private val newPassword: String
)
