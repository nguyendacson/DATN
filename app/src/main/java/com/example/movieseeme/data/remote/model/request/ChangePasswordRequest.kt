package com.example.movieseeme.data.remote.model.request

data class ChangePasswordRequest(
    val password: String,
    val newPassword: String
)