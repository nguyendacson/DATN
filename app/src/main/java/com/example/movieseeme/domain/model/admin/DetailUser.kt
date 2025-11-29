package com.example.movieseeme.domain.model.admin

import java.time.LocalDate

data class DetailUser(
    val name: String,
    val username: String,
    val avatar: String,
    val email: String,
    val dob: LocalDate?,
    val emailVerified: Boolean,
    val roles: List<Roles>,
    val provider: String
)

data class Roles(
    val name: String,
    val description: String,
    val permissions: List<Permissions>
)
data class Permissions(
    val name: String,
    val description: String,
)


