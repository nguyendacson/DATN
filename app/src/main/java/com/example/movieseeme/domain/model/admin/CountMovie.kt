package com.example.movieseeme.domain.model.admin

data class CountMovie(
    val movieId: String,
    val name: String,
    val slug:String,
    val watchCount: Long
)
