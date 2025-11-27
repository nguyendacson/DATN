package com.example.movieseeme.domain.model.movie

data class Comment(
    val id: String,
    val user: String,
    val name: String,
    val avatar: String,
    val movie: String,
    val content: String,
    val owner: Boolean,
    val createdAt: String,
    val updatedAt: String
)