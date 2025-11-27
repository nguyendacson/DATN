package com.example.movieseeme.data.remote.model.request.auth

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val authenticated: Boolean
)
