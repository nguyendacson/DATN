package com.example.movieseeme.domain.model.movie

import com.google.gson.annotations.SerializedName

data class DataMovie(
    var id:String,
    val name: String,
    val slug: String,
    val filename: String,
    @SerializedName("link_embed") val linkEmbed: String,
    @SerializedName("link_m3u8") val linkM3u8: String,
)
