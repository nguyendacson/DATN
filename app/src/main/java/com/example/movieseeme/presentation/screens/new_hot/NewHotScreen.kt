package com.example.movieseeme.presentation.screens.new_hot

import CustomToast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.lazy.HotBannerPager
import com.example.movieseeme.presentation.components.movies.lazy.HotOption
import com.example.movieseeme.presentation.screens.HeaderScreen
import com.example.movieseeme.presentation.viewmodels.movie.HotViewModel
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel

@Composable
fun NewHotScreen(
    navController: NavController,
    hotViewModel: HotViewModel,
    interactionViewModel: InteractionViewModel,
) {
    val movies by hotViewModel.listMovieHotFilter.collectAsState()
    val uiState by hotViewModel.uiState.collectAsState()
    val interactionState by interactionViewModel.uiStateAction.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(interactionState.message) {
        val msg = interactionState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                search = { navController.navigate("search") })

            HotOption(
                modifier = Modifier.size(140.dp, 30.dp),
                viewModel = hotViewModel
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (!movies.isEmpty()) {
                    HotBannerPager(
                        modifier = Modifier.fillMaxSize(),
                        movies = movies.take(20),
                        myListClick = { id ->
                            interactionViewModel.postMovieToMyList(id)
                        }
                    )
                }
                if (uiState.isLoading) {
                    LoadingBounce(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

        }
        if (showToast && toastMessage.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CustomToast(
                    value = toastMessage,
                    onDismiss = {
                        showToast = false
                        interactionViewModel.clearMessage()
                    }
                )
            }
        }
    }
}