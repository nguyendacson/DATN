package com.example.movieseeme.domain.model.movie

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    val id: String,
    val name: String,
    val slug: String,
    val type: String,
    @SerializedName("origin_name") val originName: String,
    @SerializedName("poster_url") val posterUrl: String,
    @SerializedName("thumb_url") val thumbUrl: String,
    @SerializedName("episode_current") val episodeCurrent: String,
    @SerializedName("episode_total") val episodeTotal: String,
    val time: String,
    val content: String,
    val year: String,
    val quality: String,
    val directors: Set<Director>
)
