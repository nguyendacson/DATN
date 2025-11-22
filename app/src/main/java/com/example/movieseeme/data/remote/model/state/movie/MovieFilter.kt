package com.example.movieseeme.data.remote.model.state.movie

data class MovieFilter(
    val type: String = "hoathinh",
    val page: Int? = 0,
    val limit: Int? = 200,
    val sortField: String? = "modified",
//    val sortType: String? = "desc",
//    val sortLang: String? = null,
    val status: String? = "ongoing",
    val category: String? = null,
//    val country: String? = null,
    val keySearch: String? = null,
    val year: Int? = 2025,
){

}
