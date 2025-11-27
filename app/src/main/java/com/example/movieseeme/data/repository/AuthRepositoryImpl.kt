package com.example.movieseeme.data.repository

import com.example.movieseeme.data.remote.api.AuthAPI
import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.request.auth.LoginRequest
import com.example.movieseeme.data.remote.model.request.auth.LoginResponse
import com.example.movieseeme.data.remote.model.request.auth.SignUpRequest
import com.example.movieseeme.data.remote.model.request.auth.forgot_password.ResetPassRequest
import com.example.movieseeme.domain.repository.AuthRepository
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named


class AuthRepositoryImpl @Inject constructor(
    @param:Named("auth_api") private val apiUserAPI: AuthAPI
) : AuthRepository {

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

    override suspend fun loginUser(user: LoginRequest): ApiResult<ApiResponse<LoginResponse>> {
        return safeApiCall { apiUserAPI.loginUser(user) }
    }

    override suspend fun signUp(user: SignUpRequest): ApiResult<ApiResponse<Any>> {
        return safeApiCall { apiUserAPI.signUp(user) }
    }

    override suspend fun forgotPassword(email: String): ApiResult<ApiResponse<Any>> {
        return safeApiCall { apiUserAPI.forgotPassword(email) }
    }

    override suspend fun resetPassword(resetPassRequest: ResetPassRequest): ApiResult<ApiResponse<String>> {
        return safeApiCall { apiUserAPI.resetPassword(resetPassRequest) }
    }
}

