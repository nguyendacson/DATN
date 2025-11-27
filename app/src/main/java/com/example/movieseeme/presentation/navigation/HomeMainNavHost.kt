package com.example.movieseeme.presentation.navigation

import HomeScreen
import NavBottom
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieseeme.presentation.screens.new_hot.NewHotScreen
import com.example.movieseeme.presentation.screens.profile.ProfileScreen
import com.example.movieseeme.presentation.viewmodels.movie.home.HomeViewModel
import com.example.movieseeme.presentation.viewmodels.movie.hot_new.HotViewModel
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import com.example.movieseeme.presentation.viewmodels.movie.profile.UserViewModel

@Composable
fun HomeMainNavHost(
    rootNavController: NavController,
    homeViewModel: HomeViewModel,
    interactionViewModel: InteractionViewModel,
    hotViewModel: HotViewModel,
    userViewModel: UserViewModel
) {
    val homeNavController = rememberNavController()
    Scaffold(
        bottomBar = { NavBottom(navController = homeNavController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
        ) {
            NavHost(
                navController = homeNavController,
                startDestination = "home_main"
            ) {
                composable("home_main") {
                    HomeScreen(
                        homeViewModel = homeViewModel,
                        interactionViewModel = interactionViewModel,
                        navController = rootNavController
                    )
                }
                composable("new_hot") {
                    NewHotScreen(
                        interactionViewModel = interactionViewModel,
                        hotViewModel = hotViewModel,
                        navController = rootNavController
                    )
                }
                composable("profile") {
                    ProfileScreen(
                        navController = rootNavController,
                        interactionViewModel = interactionViewModel,
                        userViewModel = userViewModel
                    )
                }
            }
        }
    }
}