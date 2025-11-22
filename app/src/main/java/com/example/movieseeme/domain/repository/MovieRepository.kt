package com.example.movieseeme.domain.repository

import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.domain.model.movie.Category
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.domain.model.movie.MovieWatching
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieRepository {
    suspend fun getAllCategory(): ApiResult<ApiResponse<List<Category>>>

    suspend fun getMoviesByFilter(
        type: String,
        page: Int = 0,
        limit: Int = 20,
        sortField: String = "created",
        sortType: String = "desc",
        sortLang: String? = null,
        status: String? = null,
        category: String? = null,
        country: String? = null,
        year: Int? = null
    ): ApiResult<ApiResponse<List<MovieDTO>>>

    suspend fun getMoviesForYou(
        page: Int = 0,
        limit: Int = 20,
        type: String? = null,
        category: String? = null,
        year: Int? = null
    ): ApiResult<ApiResponse<List<MovieDTO>>>

    //    WatchingList
    suspend fun getMoviesWatching(): ApiResult<ApiResponse<List<MovieWatching>>>
    suspend fun deleteWatching(
        dataMovieId: String
    ): ApiResult<ApiResponse<String>>

    //    Trailer
    suspend fun getMoviesTrailers(
        sortType: String = "desc",
        sortBy: String = "createdAt",
        type: String? = null,
    ): ApiResult<ApiResponse<List<MovieDTO>>>

    suspend fun postMovieToTrailers(
        movieId: String,
    ): ApiResult<ApiResponse<String>>

    suspend fun deleteMovieToTrailers(
        movieId: String,
    ): ApiResult<ApiResponse<String>>

//    Likes
    suspend fun getMoviesLike(
        sortType: String = "desc",
        sortBy: String = "createdAt",
    ): ApiResult<ApiResponse<List<MovieDTO>>>

    suspend fun postMovieToLike(
        movieId: String,
    ): ApiResult<ApiResponse<String>>

    suspend fun deleteMovieToLike(
        movieId: String,
    ): ApiResult<ApiResponse<String>>

//  MyList
    suspend fun getMoviesMyList(
        sortType: String = "desc",
        sortBy: String = "createdAt",
    ): ApiResult<ApiResponse<List<MovieDTO>>>

    suspend fun postMovieToMyList(
        movieId: String,
    ): ApiResult<ApiResponse<String>>

    suspend fun deleteMovieToMyList(
        movieId: String,
    ): ApiResult<ApiResponse<String>>

//    Search
    suspend fun getMoviesSearch(
        key: String? = null,
    ): ApiResult<ApiResponse<List<MovieDTO>>>



}
