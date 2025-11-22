package com.example.movieseeme.data.remote.model.request

import java.time.LocalDate

data class UserUpdateRequest(
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val dob: LocalDate? = null
)

