package com.example.movieseeme.domain.model.user

data class InformationUser(
    val name: String?,
    val username: String,
    val avatar: String,
    val email: String?,
    val dob: String?,
    val emailVerified: Boolean?,
    val provider: String?
)
