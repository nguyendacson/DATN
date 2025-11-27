package com.example.movieseeme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieseeme.data.local.SessionManager
import com.example.movieseeme.data.local.SplashViewModel
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.screens.login.LoginScreen
import com.example.movieseeme.presentation.screens.signUp.SignUpScreen
import com.example.movieseeme.presentation.viewmodels.auth.AuthViewModel

@Composable
fun AppNavHost(
    sessionManager: SessionManager
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val rootNavController = rememberNavController()
    val viewModel: SplashViewModel = hiltViewModel()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    val isLoggedOut by sessionManager.isLoggedOut.collectAsState()

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            rootNavController.popBackStack(rootNavController.graph.startDestinationId, true)
            rootNavController.navigate("login") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
            sessionManager.resetLogout()
        }
    }


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
            LoginScreen(
                navController = rootNavController,
                authViewModel = authViewModel
            )
        }
        composable("signUp") {
            SignUpScreen(
                navController = rootNavController,
                authViewModel = authViewModel
            )
        }
        composable("home") {
            HomeNavHost(
                authViewModel = authViewModel,
                rootNavController = rootNavController
            )
        }
        composable("loading") {
            LoadingBounce()
        }
    }
}