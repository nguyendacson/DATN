package com.example.movieseeme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.movieseeme.presentation.navigation.AppNavHost
import com.example.movieseeme.presentation.theme.MovieSeeMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieSeeMeTheme(
//                dynamicColor = false // ⚡ tắt dynamic color,
            ) {
                AppNavHost()
            }
        }
    }
}
