package com.example.movieseeme.presentation.navigation

import EpisodeScreen
import OptionCategory
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieseeme.presentation.screens.setting.InformationAppScreen
import com.example.movieseeme.presentation.screens.help.ChatScreen
import com.example.movieseeme.presentation.screens.detail.DetailScreen
import com.example.movieseeme.presentation.screens.full_list.FullListProfile
import com.example.movieseeme.presentation.screens.full_list.FullMovieScreen
import com.example.movieseeme.presentation.screens.profile.AvatarPicker
import com.example.movieseeme.presentation.screens.profile.AvatarScreen
import com.example.movieseeme.presentation.screens.search.SearchScreen
import com.example.movieseeme.presentation.screens.setting.EmailScreen
import com.example.movieseeme.presentation.screens.setting.GeneralScreen
import com.example.movieseeme.presentation.screens.setting.MenuSettingScreen
import com.example.movieseeme.presentation.screens.setting.MyAccountScreen
import com.example.movieseeme.presentation.screens.setting.NameScreen
import com.example.movieseeme.presentation.screens.setting.PasswordScreen
import com.example.movieseeme.presentation.screens.setting.ThemeSetScreen
import com.example.movieseeme.presentation.viewmodels.admin.AdminViewModel
import com.example.movieseeme.presentation.viewmodels.auth.AuthViewModel
import com.example.movieseeme.presentation.viewmodels.auth.ThemeViewModel
import com.example.movieseeme.presentation.viewmodels.movie.CommentViewModel
import com.example.movieseeme.presentation.viewmodels.movie.EpisodeViewModel
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import com.example.movieseeme.presentation.viewmodels.movie.detail.DetailViewModel
import com.example.movieseeme.presentation.viewmodels.movie.home.HomeViewModel
import com.example.movieseeme.presentation.viewmodels.movie.hot_new.HotViewModel
import com.example.movieseeme.presentation.viewmodels.movie.profile.UserViewModel
import com.example.movieseeme.presentation.viewmodels.search.SearchViewModel

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
    val detailViewModel: DetailViewModel = hiltViewModel()
    val commentViewModel: CommentViewModel = hiltViewModel()
    val episodeViewModel: EpisodeViewModel = hiltViewModel()
    val adminViewModel: AdminViewModel = hiltViewModel()

    val homeNavController = rememberNavController()

    NavHost(
        navController = homeNavController,
        startDestination = "home_main"
    ) {
        composable("home_main") {
            HomeMainNavHost(
                homeNavController,
                homeViewModel = homeViewModel,
                hotViewModel = hotViewModel,
                interactionViewModel = interactionViewModel,
                userViewModel = userViewModel,
                adminViewModel = adminViewModel
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

        composable("detailScreen/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            DetailScreen(
                movieId = movieId,
                detailViewModel = detailViewModel,
                interactionViewModel = interactionViewModel,
                commentViewModel = commentViewModel,
                userViewModel = userViewModel,
                navController = homeNavController
            )
        }

        composable("episode?movieId={movieId}&episodeId={episodeId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            val episodeId = backStackEntry.arguments?.getString("episodeId") ?: ""
            EpisodeScreen(
                movieId = movieId,
                episodeId = episodeId,
                episodeViewModel = episodeViewModel,
                interactionViewModel = interactionViewModel,
                commentViewModel = commentViewModel,
                userViewModel = userViewModel,
                detailViewModel = detailViewModel,
                navController = homeNavController
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
                interactionViewModel = interactionViewModel
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

        composable("help") {
            ChatScreen(
                navController = homeNavController,
                interactionViewModel = interactionViewModel
            )
        }

        composable("information_app") {
            InformationAppScreen(
                navController = homeNavController
            )
        }

    }

}
