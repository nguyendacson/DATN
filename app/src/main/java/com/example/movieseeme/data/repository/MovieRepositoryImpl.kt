package com.example.movieseeme.data.repository

import com.example.movieseeme.data.remote.api.MovieAPI
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
import com.example.movieseeme.domain.repository.MovieRepository
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class MovieRepositoryImpl @Inject constructor(
    @param:Named("movie_retrofit") private val movieAPI: MovieAPI
) : MovieRepository {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<ApiResponse<T>>): ApiResult<ApiResponse<T>> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.Error(response.code(), "Empty response body")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ApiResponse::class.java)
                } catch (e: Exception) {
                    null
                }
                ApiResult.Error(
                    code = errorResponse?.code ?: response.code(),
                    message = errorResponse?.message ?: "Unknown error"
                )
            }
        } catch (e: IOException) {
            ApiResult.Error(null, "Network error: ${e.message}")
        } catch (e: Exception) {
            ApiResult.Error(null, "Unknown error: ${e.message}")
        }
    }

    override suspend fun getAllCategory(): ApiResult<ApiResponse<List<Category>>> {
        return safeApiCall { movieAPI.getAllCategory() }
    }

    override suspend fun getMoviesByFilter(
        type: String,
        page: Int,
        limit: Int,
        sortField: String,
        sortType: String,
        sortLang: String?,
        status: String?,
        category: String?,
        country: String?,
        year: Int?
    ): ApiResult<ApiResponse<List<MovieDTO>>> {
        return safeApiCall {
            movieAPI.getMoviesByFilter(
                type = type,
                page = page,
                limit = limit,
                sortField = sortField,
                sortType = sortType,
                sortLang = sortLang,
                status = status,
                category = category,
                country = country,
                year = year
            )
        }
    }

    override suspend fun getMoviesForYou(
        page: Int,
        limit: Int,
        type: String?,
        category: String?,
        year: Int?
    ): ApiResult<ApiResponse<List<MovieDTO>>> {
        return safeApiCall {
            movieAPI.getMoviesForYou(
                page = page,
                type = type,
                limit = limit,
                category = category,
                year = year
            )
        }
    }

    //    SEARCH
    override suspend fun getMoviesSearch(
        key: String?
    ): ApiResult<ApiResponse<List<MovieDTO>>> {
        return safeApiCall {
            movieAPI.getMoviesSearch(
                key = key
            )
        }
    }

    //    WATCHING
    override suspend fun getMoviesWatching(): ApiResult<ApiResponse<List<MovieWatching>>> {
        return safeApiCall { movieAPI.getMoviesWatching() }
    }

    override suspend fun updateWatching(watchingUpdateRequest: WatchingUpdateRequest): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.updateWatching(watchingUpdateRequest)
        }
    }

    override suspend fun postMoviesWatching(watchingCreateRequest: WatchingCreateRequest): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.postMoviesWatching(
                watchingCreateRequest = watchingCreateRequest
            )
        }
    }

    override suspend fun deleteWatching(
        dataMovieId: String
    ): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.deleteWatching(
                dataMovieId = dataMovieId
            )
        }
    }

    //    TRAILER
    override suspend fun getMoviesTrailers(
        sortType: String,
        sortBy: String,
        type: String?
    ): ApiResult<ApiResponse<List<MovieDTO>>> {
        return safeApiCall {
            movieAPI.getMoviesTrailers(
                sortType = sortType,
                sortBy = sortBy,
                type = type
            )
        }
    }

    override suspend fun postMovieToTrailers(movieId: String): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.postMovieToTrailers(
                movieId = movieId
            )
        }
    }

    override suspend fun deleteMovieToTrailers(movieId: String): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.deleteMovieToTrailers(
                movieId = movieId
            )
        }
    }

    //    LIKES
    override suspend fun getMoviesLike(
        sortType: String,
        sortBy: String
    ): ApiResult<ApiResponse<List<MovieDTO>>> {
        return safeApiCall {
            movieAPI.getMoviesLike(
                sortType = sortType,
                sortBy = sortBy
            )
        }
    }

    override suspend fun postMovieToLike(movieId: String): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.postMovieToLike(
                movieId = movieId
            )
        }
    }

    override suspend fun deleteMovieToLike(movieId: String): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.deleteMovieToLike(
                movieId = movieId
            )
        }
    }

    //    MY LIST
    override suspend fun getMoviesMyList(
        sortType: String,
        sortBy: String
    ): ApiResult<ApiResponse<List<MovieDTO>>> {
        return safeApiCall {
            movieAPI.getMoviesMyList(
                sortType = sortType,
                sortBy = sortBy
            )
        }
    }

    override suspend fun postMovieToMyList(movieId: String): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.postMovieToMyList(
                movieId = movieId
            )
        }
    }

    override suspend fun deleteMovieToMyList(movieId: String): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.deleteMovieToMyList(
                movieId = movieId
            )
        }
    }

    //    Detail
    override suspend fun getMovieById(movieId: String): ApiResult<ApiResponse<MovieDTO>> {
        return safeApiCall {
            movieAPI.getMovieById(
                movieId = movieId
            )
        }
    }

    override suspend fun getActorById(movieId: String): ApiResult<ApiResponse<List<String>>> {
        return safeApiCall {
            movieAPI.getActorById(
                movieId = movieId
            )
        }
    }

    override suspend fun postComment(commentRequest: CommentCreateRequest): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.postComment(
                commentRequest = commentRequest
            )
        }
    }

    override suspend fun getComments(
        movieId: String,
        sort: String
    ): ApiResult<ApiResponse<List<Comment>>> {
        return safeApiCall {
            movieAPI.getComments(
                movieId = movieId,
                sort = sort,
            )
        }
    }


    override suspend fun updateComment(commentPatchRequest: CommentPatchRequest): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.updateComment(
                commentPatchRequest = commentPatchRequest
            )
        }
    }

    override suspend fun deleteComment(commentId: String): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.deleteComment(
                commentId = commentId
            )
        }
    }

    // Episode
    override suspend fun getEpisodes(movieId: String): ApiResult<ApiResponse<List<Episode>>> {
        return safeApiCall {
            movieAPI.getEpisodes(
                movieId = movieId
            )
        }
    }

    override suspend fun chat(
        file: MultipartBody.Part?,
        message: String?
    ): ApiResult<ApiResponse<String>> {
        return safeApiCall {
            movieAPI.chat(
                file = file,
                message = message
            )
        }
    }
}
