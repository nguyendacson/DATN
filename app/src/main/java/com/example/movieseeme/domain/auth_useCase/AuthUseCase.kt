package com.example.movieseeme.domain.auth_useCase

import com.example.movieseeme.data.local.TokenManager
import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.request.auth.LoginRequest
import com.example.movieseeme.data.remote.model.request.auth.LoginResponse
import com.example.movieseeme.data.remote.model.request.auth.SignUpRequest
import com.example.movieseeme.data.remote.model.request.auth.forgot_password.ResetPassRequest
import com.example.movieseeme.data.repository.AuthRepositoryImpl
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepositoryImpl,
    val tokenManager: TokenManager
) {
    suspend fun login(request: LoginRequest): ApiResult<ApiResponse<LoginResponse>> {
        val result = repository.loginUser(request)
//        if (result is ApiResult.Success) {
//            result.data.result.let { token ->
//                tokenManager.saveTokens(
//                    accessToken = token.accessToken,
//                    refreshToken = token.refreshToken
//                )
//            }
//        }
        return result
    }

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        tokenManager.saveTokens(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    suspend fun signUp(request: SignUpRequest): ApiResult<ApiResponse<Any>> {
        return repository.signUp(request)
    }

    suspend fun forgotPassword(request: String): ApiResult<ApiResponse<Any>> {
        return repository.forgotPassword(request)
    }

    suspend fun resetPassword(request: ResetPassRequest): ApiResult<ApiResponse<String>> {
        return repository.resetPassword(request)
    }

    suspend fun cleanTokenUser() = tokenManager.clearTokens()
}