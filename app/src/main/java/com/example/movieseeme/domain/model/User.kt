package com.example.movieseeme.domain.model

import java.time.LocalDate

data class User(
    val name: String,
    val username: String,
    val password: String,
    val email: String?,
    val avatar: String,
    val dob: LocalDate,
    val emailVerified: Boolean
)