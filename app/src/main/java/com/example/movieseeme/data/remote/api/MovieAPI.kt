package com.example.movieseeme.data.remote.api

import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.request.WatchingCreateRequest
import com.example.movieseeme.data.remote.model.request.WatchingUpdateRequest
import com.example.movieseeme.data.remote.model.request.comment.CommentCreateRequest
import com.example.movieseeme.data.remote.model.request.comment.CommentPatchRequest
import com.example.movieseeme.domain.model.movie.Category
import com.example.movieseeme.domain.model.movie.Comment
import com.example.movieseeme.domain.model.movie.Episode
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.domain.model.movie.MovieWatching
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

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

    @POST("movies/watchlist")
    suspend fun postMoviesWatching(
        @Body watchingCreateRequest: WatchingCreateRequest
    ): Response<ApiResponse<String>>

    @PATCH("movies/update/watchlist")
    suspend fun updateWatching(
        @Body watchingUpdateRequest: WatchingUpdateRequest
    ): Response<ApiResponse<String>>


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
    @GET("movies/search/{key}")
    suspend fun getMoviesSearch(
        @Path("key") key: String? = null,
    ): Response<ApiResponse<List<MovieDTO>>>

    // Detail
    @GET("movies/{movie_Id}")
    suspend fun getMovieById(
        @Path("movie_Id") movieId: String
    ): Response<ApiResponse<MovieDTO>>

    @GET("movies/{movieId}/actors")
    suspend fun getActorById(
        @Path("movieId") movieId: String,
    ): Response<ApiResponse<List<String>>>

    // comment
    @POST("movies/comments")
    suspend fun postComment(
        @Body commentRequest: CommentCreateRequest
    ): Response<ApiResponse<String>>

    @GET("movies/{movieId}/comments")
    suspend fun getComments(
        @Path("movieId") movieId: String,
        @Query("sort") sort: String = "desc",
        @Query("sortBy") sortBy: String = "createdAt"
    ): Response<ApiResponse<List<Comment>>>

    @PATCH("movies/update/comments")
    suspend fun updateComment(
        @Body commentPatchRequest: CommentPatchRequest
    ): Response<ApiResponse<String>>

    @DELETE("movies/comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: String
    ): Response<ApiResponse<String>>

    //    Episode
    @GET("movies/{movieId}/episode")
    suspend fun getEpisodes(
        @Path("movieId") movieId: String,
    ): Response<ApiResponse<List<Episode>>>

    // chat
    @Multipart
    @POST("chat")
    suspend fun chat(
        @Part file:MultipartBody.Part?,
        @Part("message") message: String?,
    ): Response<ApiResponse<String>>
}
