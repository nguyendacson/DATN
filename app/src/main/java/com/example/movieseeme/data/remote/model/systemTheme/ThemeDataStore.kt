package com.example.movieseeme.data.remote.model.systemTheme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.themeDataStore by preferencesDataStore("theme_prefs")

class ThemeDataStore(private val context: Context) {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme_mode")
    }

    val themeFlow = context.themeDataStore.data.map { prefs ->
        when (prefs[THEME_KEY]) {
            ThemeMode.LIGHT.name -> ThemeMode.LIGHT
            ThemeMode.DARK.name -> ThemeMode.DARK
            ThemeMode.SYSTEM.name -> ThemeMode.SYSTEM
            else -> ThemeMode.SYSTEM   // fallback
        }
    }


    suspend fun saveTheme(mode: ThemeMode) {
        context.themeDataStore.edit { prefs ->
            prefs[THEME_KEY] = mode.name
        }
    }
}