package com.example.movieseeme.data.remote.model.request

data class WatchingCreateRequest(
    val movieId: String,
    val dataMovieId: String,
    val progressSeconds: Int
)
