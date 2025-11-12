package com.example.movieseeme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieseeme.data.local.SplashViewModel
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.screens.home.HomeScreen
import com.example.movieseeme.presentation.screens.login.LoginScreen
import com.example.movieseeme.presentation.screens.signUp.SignUpScreen

@Composable
fun AppNavHost() {
    val rootNavController = rememberNavController()
    val viewModel: SplashViewModel = hiltViewModel()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    val startDestination = when (isLoggedIn) {
        true -> "home"
        false -> "login"
        null -> "loading"
    }

    NavHost(
        navController = rootNavController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(navController = rootNavController)
        }
        composable("signUp") {
            SignUpScreen(navController = rootNavController)
        }
        composable("home") {
            HomeScreen(rootNavController)
        }
        composable("loading") {
            LoadingBounce()
        }
    }
}