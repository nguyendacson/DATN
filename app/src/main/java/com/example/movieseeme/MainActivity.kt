package com.example.movieseeme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.movieseeme.data.local.SessionManager
import com.example.movieseeme.data.remote.model.systemTheme.ThemeDataStore
import com.example.movieseeme.data.remote.model.systemTheme.ThemeMode
import com.example.movieseeme.presentation.navigation.AppNavHost
import com.example.movieseeme.presentation.theme.MovieSeeMeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager
    private val themeDataStore by lazy { ThemeDataStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by themeDataStore.themeFlow.collectAsState(initial = ThemeMode.SYSTEM)

            MovieSeeMeTheme(themeMode = themeMode) {
                AppNavHost(sessionManager = sessionManager)
            }
        }
    }
}
