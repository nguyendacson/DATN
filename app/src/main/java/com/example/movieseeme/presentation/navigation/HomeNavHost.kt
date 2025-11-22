package com.example.movieseeme.presentation.navigation

import OptionCategory
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieseeme.presentation.screens.FullListProfile
import com.example.movieseeme.presentation.screens.FullMovieScreen
import com.example.movieseeme.presentation.screens.HomeNavigation
import com.example.movieseeme.presentation.screens.SearchScreen
import com.example.movieseeme.presentation.screens.profile.AvatarPicker
import com.example.movieseeme.presentation.screens.profile.AvatarScreen
import com.example.movieseeme.presentation.screens.setting.EmailScreen
import com.example.movieseeme.presentation.screens.setting.GeneralScreen
import com.example.movieseeme.presentation.screens.setting.MenuSettingScreen
import com.example.movieseeme.presentation.screens.setting.MyAccountScreen
import com.example.movieseeme.presentation.screens.setting.NameScreen
import com.example.movieseeme.presentation.screens.setting.PasswordScreen
import com.example.movieseeme.presentation.screens.setting.ThemeSetScreen
import com.example.movieseeme.presentation.viewmodels.movie.HomeViewModel
import com.example.movieseeme.presentation.viewmodels.movie.HotViewModel
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import com.example.movieseeme.presentation.viewmodels.movie.SearchViewModel
import com.example.movieseeme.presentation.viewmodels.user.AuthViewModel
import com.example.movieseeme.presentation.viewmodels.user.ThemeViewModel
import com.example.movieseeme.presentation.viewmodels.user.UserViewModel

@Composable
fun HomeNavHost(
    authViewModel: AuthViewModel,
    rootNavController: NavHostController
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val interactionViewModel: InteractionViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val hotViewModel: HotViewModel = hiltViewModel()

    val homeNavController = rememberNavController()

    NavHost(
        navController = homeNavController,
        startDestination = "home_main"
    ) {
        composable("home_main") {
            HomeNavigation(
                homeNavController,
                homeViewModel = homeViewModel,
                hotViewModel = hotViewModel,
                interactionViewModel = interactionViewModel,
                userViewModel = userViewModel
            )
        }

        composable("optionCategory") {
            OptionCategory(
                navController = homeNavController,
                homeViewModel = homeViewModel
            )
        }

        composable("search") {
            SearchScreen(
                navController = homeNavController,
                interactionViewModel = interactionViewModel,
                searchViewModel = searchViewModel
            )
        }

        composable("fullMovie/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            FullMovieScreen(
                navController = homeNavController,
                viewModel = homeViewModel,
                category = category,
            )
        }

        composable("fullListMovie/{slug}") { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: ""
            FullListProfile(
                navController = homeNavController,
                interactionViewModel = interactionViewModel,
                slug = slug,
            )
        }

        composable("avatar") {
            AvatarScreen(
                navController = homeNavController,
                userViewModel = userViewModel
            )
        }

        composable("chose_avatar") {
            AvatarPicker(
                navController = homeNavController,
                userViewModel = userViewModel
            )
        }

        composable("menu_setting") {
            MenuSettingScreen(
                navController = homeNavController,
            )
        }

        composable("general_setting") {
            GeneralScreen(
                navController = homeNavController,
                themeViewModel = themeViewModel
            )
        }

        composable("themeSet") {
            ThemeSetScreen(
                navController = homeNavController,
                themeViewModel = themeViewModel
            )
        }

        composable("myAccount") {
            MyAccountScreen(
                navController = homeNavController,
                userViewModel = userViewModel,
                authViewModel = authViewModel,
                rootNavController = rootNavController
            )
        }

        composable("nameScreen") {
            NameScreen(
                navController = homeNavController,
                userViewModel = userViewModel
            )
        }
        composable("emailScreen") {
            EmailScreen(
                navController = homeNavController,
                userViewModel = userViewModel
            )
        }
        composable("userAndPasswordScreen") {
            PasswordScreen(
                navController = homeNavController,
                userViewModel = userViewModel
            )
        }

    }

}
