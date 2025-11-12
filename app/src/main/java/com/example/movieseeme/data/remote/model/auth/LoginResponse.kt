package com.example.movieseeme.data.remote.model.auth

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val authenticated: Boolean
)
