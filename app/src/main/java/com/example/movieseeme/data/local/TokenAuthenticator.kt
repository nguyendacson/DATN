package com.example.movieseeme.data.local

import com.example.movieseeme.data.remote.api.AuthAPI
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Named

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    @param:Named("auth_api") private val authAPI: AuthAPI,
    private val sessionManager: SessionManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization") != null) {
            val newAccessToken = runBlocking {
                val refreshToken = tokenManager.refreshToken.firstOrNull()
                if (refreshToken != null) {
                    try {
                        val refreshToken = authAPI.refreshToken(refreshToken)
                        if (refreshToken.success == true && refreshToken.result != null) {
                            tokenManager.saveTokens(
                                refreshToken.result.accessToken,
                                refreshToken.result.refreshToken
                            )
                        }
                        return@runBlocking refreshToken.result.accessToken
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return@runBlocking null
                    }

                }
                sessionManager.logout()
                null
            }
            return newAccessToken?.let {
                response.request.newBuilder()
                    .header("Authorization", "Bearer $it")
                    .build()
            }
        }
        return null
    }
}