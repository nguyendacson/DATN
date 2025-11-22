package com.example.movieseeme.domain.model.user

data class Signature(
    val timestamp: Long,
    val signature: String,
    val folder: String,
    val cloudName: String,
    val apiKey: String
)