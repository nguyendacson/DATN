package com.example.movieseeme.domain.model.user

import java.time.LocalDate

data class Permission(
    val name: String,
    val description: String
)

data class Role(
    val name: String,
    val description: String,
    val permission: Set<Permission>
)

data class UserResponseAdmin(
    val name: String,
    val username: String,
    val avatar: String,
    val email: String,
    val dob: LocalDate,
    val emailVerified: Boolean,
    val roles: Set<Role>,
    val provider: String
)