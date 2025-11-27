package com.example.movieseeme.data.remote.model.request

data class WatchingUpdateRequest(
    val dataMovieId: String,
    val newProgressSeconds: Int
)
