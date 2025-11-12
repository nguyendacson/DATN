package com.example.movieseeme.domain.usecase

import com.example.movieseeme.data.local.TokenManager
import com.example.movieseeme.data.remote.model.ApiResponse
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.auth.LoginRequest
import com.example.movieseeme.data.remote.model.auth.LoginResponse
import com.example.movieseeme.data.remote.model.auth.SignUpRequest
import com.example.movieseeme.data.remote.model.auth.forgot_password.ResetPassRequest
import com.example.movieseeme.data.repository.UserRepositoryImpl
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: UserRepositoryImpl,
    private val tokenManager: TokenManager
) {
    suspend operator fun invoke(request: LoginRequest): ApiResult<ApiResponse<LoginResponse>> {
        val result = repository.loginUser(request)
        if (result is ApiResult.Success) {
            result.data.result.let { token ->
                tokenManager.saveTokens(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken
                )
            }
        }
        return result
    }

    suspend operator fun invoke(accessToken: String, refreshToken: String) {
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