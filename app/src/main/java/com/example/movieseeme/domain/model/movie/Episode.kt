package com.example.movieseeme.domain.model.movie

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("server_name") val serverName: String,
    @SerializedName("server_data") val dataMovie: List<DataMovie>
)
