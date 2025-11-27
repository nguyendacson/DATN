package com.example.movieseeme.domain.repository

import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
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
import retrofit2.http.Part

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

    suspend fun postMoviesWatching(watchingCreateRequest: WatchingCreateRequest): ApiResult<ApiResponse<String>>
    suspend fun getMoviesWatching(): ApiResult<ApiResponse<List<MovieWatching>>>

    suspend fun updateWatching(
        watchingUpdateRequest: WatchingUpdateRequest
    ): ApiResult<ApiResponse<String>>

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

    //  Detail
    suspend fun getMovieById(
        movieId: String,
    ): ApiResult<ApiResponse<MovieDTO>>

    suspend fun getActorById(
        movieId: String,
    ): ApiResult<ApiResponse<List<String>>>

    // comment
    suspend fun postComment(
        commentRequest: CommentCreateRequest
    ): ApiResult<ApiResponse<String>>

    suspend fun getComments(
        movieId: String,
        sort: String = "desc",
    ): ApiResult<ApiResponse<List<Comment>>>

    suspend fun updateComment(
        commentPatchRequest: CommentPatchRequest
    ): ApiResult<ApiResponse<String>>

    suspend fun deleteComment(
        commentId: String
    ): ApiResult<ApiResponse<String>>

    // Episode
    suspend fun getEpisodes(
        movieId: String,
    ): ApiResult<ApiResponse<List<Episode>>>

    // chat
    suspend fun chat(
        @Part file: MultipartBody.Part?,
        @Part("message") message: String?,
    ): ApiResult<ApiResponse<String>>
}
