package com.example.movieseeme.di

import com.example.movieseeme.data.local.TokenAuthenticator
import com.example.movieseeme.data.remote.api.AuthAPI
import com.example.movieseeme.data.remote.api.MovieAPI
import com.example.movieseeme.data.remote.api.UserAPI
import com.example.movieseeme.data.remote.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://sonnd03.io.vn/apiUser/"

    @Provides
    @Singleton
    @Named("auth_client")
    fun provideAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    @Named("auth_retrofit")
    fun provideAuthRetrofit(@Named("auth_client") authClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("auth_api")
    fun provideAuthAPI(@Named("auth_retrofit") retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    @Named("main_client")
    fun provideMainOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    @Named("main_retrofit")
    fun provideMainRetrofit(@Named("main_client") mainClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mainClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMainAuthAPI(@Named("main_retrofit") retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    @Named("movie_retrofit")
    fun provideMainMovieAPI(@Named("main_retrofit") retrofit: Retrofit): MovieAPI {
        return retrofit.create(MovieAPI::class.java)
    }

    @Provides
    @Singleton
    @Named("user_retrofit")
    fun provideMainUserAPI(@Named("main_retrofit") retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }
}
