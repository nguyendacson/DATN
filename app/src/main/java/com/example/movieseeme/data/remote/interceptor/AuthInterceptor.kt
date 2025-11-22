package com.example.movieseeme.data.remote.interceptor

import com.example.movieseeme.data.local.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    @Volatile
    private var currentToken: String? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            tokenManager.accessToken.collect {
                currentToken = it
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        runBlocking {
            tokenManager.getAccessToken()?.first()
        }

        val requestBuilder = chain.request().newBuilder()
        currentToken?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}
