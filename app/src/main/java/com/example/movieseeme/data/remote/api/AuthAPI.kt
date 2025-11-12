package com.example.movieseeme.data.remote.api

import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.domain.model.UserResponseAdmin
import com.example.movieseeme.data.remote.model.auth.LoginRequest
import com.example.movieseeme.data.remote.model.auth.LoginResponse
import com.example.movieseeme.data.remote.model.auth.SignUpRequest
import com.example.movieseeme.data.remote.model.auth.forgot_password.ResetPassRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface AuthAPI {
//    user
    @POST("auth/login")
    suspend fun loginUser(@Body userLogin: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("users")
    suspend fun signUp(@Body userSignUp: SignUpRequest): Response<ApiResponse<Any>>

    @POST("users/forgot-password")
    suspend fun forgotPassword(@Body email: String): Response<ApiResponse<Any>>

    @POST("users/reset-password")
    suspend fun resetPassword(@Body resetPassRequest: ResetPassRequest): Response<ApiResponse<String>>

// admin
    @GET("admin/allUser")
    suspend fun getAllUser(
        @Header("Authorization") token: String,

    ): ApiResponse<List<UserResponseAdmin>>

    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Body refreshToken: String
    ): ApiResponse<LoginResponse>
}