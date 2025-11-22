package com.example.movieseeme.domain.model.movie

data class MovieWatching(
    val id: String,
    val progressSeconds: Int,
    val lastWatchedAt: String,
    val dataMovieId: String,
    val dataMovieName : String,
    val movieDTO: MovieDTO
)
