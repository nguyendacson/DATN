package com.example.movieseeme.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class TokenManager @Inject constructor(
    @param:ApplicationContext private val context: Context
){
    companion object{
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
    val accessToken: Flow<String?> = context.dataStore.data.map { it[ACCESS_TOKEN]  }
    val refreshToken: Flow<String?> = context.dataStore.data.map { it[REFRESH_TOKEN]  }

    suspend fun getAccessToken(): String? = accessToken.first()
//    suspend fun getRefreshToken(): String? = refreshToken.first()

    suspend fun saveTokens(accessToken: String, refreshToken: String){
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun clearTokens(){
        context.dataStore.edit { it.clear() }
    }
}