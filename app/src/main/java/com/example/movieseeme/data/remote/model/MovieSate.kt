package com.example.movieseeme.data.remote.model

data class AppState(
    val code:Int? = null,
    val isLoading: Boolean = false,
    val message: String? = null,
    val success: Boolean? = false,
)
