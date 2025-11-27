package com.example.movieseeme

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
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

    private var canEnterPip = false

    fun setPipEnabled(enabled: Boolean) {
        canEnterPip = enabled
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val params = PictureInPictureParams.Builder()
                .setAutoEnterEnabled(enabled)
                .build()
            setPictureInPictureParams(params)
        }
    }

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

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (canEnterPip) {
            val params = PictureInPictureParams.Builder().build()
            enterPictureInPictureMode(params)
        }
    }
}