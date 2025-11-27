package com.example.movieseeme.data.remote.model.request.auth

data class SignUpRequest(
    val name: String,
    val username: String,
    val password: String,
    val email: String? = null,
)
