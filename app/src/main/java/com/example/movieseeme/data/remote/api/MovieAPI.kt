package com.example.movieseeme.data.remote.api

import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.domain.model.movie.Category
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.domain.model.movie.MovieWatching
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("movies/categories")
    suspend fun getAllCategory(): Response<ApiResponse<List<Category>>>

    @GET("movies/filter/{type}")
    suspend fun getMoviesByFilter(
        @Path("type") type: String,
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("sort_field") sortField: String = "created",
        @Query("sort_type") sortType: String = "desc",
        @Query("sort_lang") sortLang: String? = null,
        @Query("status") status: String? = null,
        @Query("category") category: String? = null,
        @Query("country") country: String? = null,
        @Query("year") year: Int? = null
    ): Response<ApiResponse<List<MovieDTO>>>

    @GET("movies/list-for-you")
    suspend fun getMoviesForYou(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("type") type: String? = null,
        @Query("category") category: String? = null,
        @Query("country") country: String? = null,
        @Query("year") year: Int? = null
    ): Response<ApiResponse<List<MovieDTO>>>


//  WatchingList
    @GET("movies/watchlist")
    suspend fun getMoviesWatching(): Response<ApiResponse<List<MovieWatching>>>

    @DELETE("movies/{dataMovieId}/watchlist")
    suspend fun deleteWatching(
        @Path("dataMovieId") dataMovieId: String
    ): Response<ApiResponse<String>>

// Trailer
    @GET("movies/trailers")
    suspend fun getMoviesTrailers(
        @Query("sort") sortType: String = "desc",
        @Query("sortBy") sortBy: String = "createdAt",
        @Query("filter") type: String? = null,
    ): Response<ApiResponse<List<MovieDTO>>>

    @POST("movies/{movieId}/trailers")
    suspend fun postMovieToTrailers(
        @Path("movieId") movieId: String,
    ): Response<ApiResponse<String>>

    @DELETE("movies/{movieId}/trailers")
    suspend fun deleteMovieToTrailers(
        @Path("movieId") movieId: String,
    ): Response<ApiResponse<String>>

// Like
    @GET("movies/likes")
    suspend fun getMoviesLike(
        @Query("sort") sortType: String = "desc",
        @Query("sortBy") sortBy: String = "createdAt",
    ): Response<ApiResponse<List<MovieDTO>>>

    @POST("movies/{movieId}/likes")
    suspend fun postMovieToLike(
        @Path("movieId") movieId: String,
    ): Response<ApiResponse<String>>

    @DELETE("movies/{movieId}/likes")
    suspend fun deleteMovieToLike(
        @Path("movieId") movieId: String,
    ): Response<ApiResponse<String>>

// My List
    @GET("movies/myList")
    suspend fun getMoviesMyList(
        @Query("sort") sortType: String = "desc",
        @Query("sortBy") sortBy: String = "createdAt",
    ): Response<ApiResponse<List<MovieDTO>>>

    @POST("movies/{movieId}/myList")
    suspend fun postMovieToMyList(
        @Path("movieId") movieId: String,
    ): Response<ApiResponse<String>>

    @DELETE("movies/{movieId}/myList")
    suspend fun deleteMovieToMyList(
        @Path("movieId") movieId: String,
    ): Response<ApiResponse<String>>

//  Search
    @POST("movies/search/{key}")
    suspend fun getMoviesSearch(
        @Path("key") key: String? = null,
    ): Response<ApiResponse<List<MovieDTO>>>
}
