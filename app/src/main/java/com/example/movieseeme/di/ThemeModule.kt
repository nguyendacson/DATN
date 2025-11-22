package com.example.movieseeme.di

import android.content.Context
import com.example.movieseeme.data.remote.model.systemTheme.ThemeDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThemeModule {

    @Provides
    @Singleton
    fun provideThemeDataStore(@ApplicationContext context: Context): ThemeDataStore {
        return ThemeDataStore(context)
    }
}