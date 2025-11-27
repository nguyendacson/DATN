package com.example.movieseeme.domain.repository

import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.request.auth.LoginRequest
import com.example.movieseeme.data.remote.model.request.auth.LoginResponse
import com.example.movieseeme.data.remote.model.request.auth.SignUpRequest
import com.example.movieseeme.data.remote.model.request.auth.forgot_password.ResetPassRequest
import retrofit2.http.Body

interface AuthRepository {
    suspend fun loginUser(@Body user: LoginRequest): ApiResult<ApiResponse<LoginResponse>>
    suspend fun signUp(user: SignUpRequest): ApiResult<ApiResponse<Any>>

    suspend fun forgotPassword(email: String): ApiResult<ApiResponse<Any>>
    suspend fun resetPassword(resetPassRequest: ResetPassRequest): ApiResult<ApiResponse<String>>
}