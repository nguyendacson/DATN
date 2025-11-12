package com.example.movieseeme.data.remote.model

data class ApiResponse<T>(
    val code: Int,
    val success: Boolean,
    val message: String,
    val result: T
)