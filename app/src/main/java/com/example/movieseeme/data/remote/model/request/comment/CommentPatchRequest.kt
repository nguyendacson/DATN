package com.example.movieseeme.data.remote.model.request.comment

data class CommentPatchRequest(
    val commentId: String,
    val newContent: String
)