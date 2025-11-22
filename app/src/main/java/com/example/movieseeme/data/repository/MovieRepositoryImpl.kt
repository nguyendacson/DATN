package com.example.movieseeme.data.repository

import com.example.movieseeme.data.remote.api.MovieAPI
import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.domain.model.enum.ProfileTitleFull
import com.example.movieseeme.domain.model.movie.Category
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.domain.model.movie.MovieWatching
import com.example.movieseeme.domain.repository.MovieRepository
import com.google.gson.Gson
import retrofit2.Response
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
                    code = response.code(),
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

}
