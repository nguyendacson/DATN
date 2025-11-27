package com.example.movieseeme.data.remote.model.request.comment

data class CommentCreateRequest(
    val movieId: String,
    val content: String,
)