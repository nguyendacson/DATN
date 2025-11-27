package com.example.movieseeme.presentation.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.systemTheme.ThemeDataStore
import com.example.movieseeme.data.remote.model.systemTheme.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeDataStore: ThemeDataStore
) : ViewModel() {

    val themeMode = themeDataStore.themeFlow

    fun setTheme(mode: ThemeMode) {
        viewModelScope.launch {
            themeDataStore.saveTheme(mode)
        }
    }
}