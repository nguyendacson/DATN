package com.example.movieseeme.presentation.screens.profile

import CustomToast
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.domain.enum_class.ProfileTitleFull
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.bottom_sheet.MovieBottomSheetContent
import com.example.movieseeme.presentation.components.lock_screen.LockScreenOrientationPortrait
import com.example.movieseeme.presentation.components.movies.item.profile.HeaderProfile
import com.example.movieseeme.presentation.components.movies.lazy.profile.RowMovie
import com.example.movieseeme.presentation.components.movies.lazy.profile.RowMovieWatching
import com.example.movieseeme.presentation.theme.extension.titleHeader
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import com.example.movieseeme.presentation.viewmodels.movie.profile.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    interactionViewModel: InteractionViewModel,
    userViewModel: UserViewModel
) {
    LockScreenOrientationPortrait()

    val movieWatching by interactionViewModel.listMovieWatching.collectAsState()
    val movieListForYou by interactionViewModel.listMovieListForYou.collectAsState()
    val movieListTrailer by interactionViewModel.listMovieTrailer.collectAsState()
    val movieListLike by interactionViewModel.listMovieLikes.collectAsState()
    val movieListMyList by interactionViewModel.listMovieMyList.collectAsState()

    val userInfo by userViewModel.user.collectAsState()

    val selectedMovie by interactionViewModel.selectedMovie.collectAsState()
    val interactionState by interactionViewModel.uiStateAction.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(interactionState.message) {
        val msg = interactionState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "Cá Nhân",
                    style = MaterialTheme.typography.titleHeader.copy(
                        fontSize = 20.sp,
                    )
                )

                IconButton(
                    onClick = {
                        navController.navigate("menu_setting")
                    }) {
                    Icon(
                        Icons.Default.Menu, tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "button menu",
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    if (userInfo != null) {
                        Spacer(modifier = Modifier.height(25.dp))
                        HeaderProfile(
                            modifier = Modifier.fillMaxWidth(),
                            userInfo = userInfo!!,
                            downloadClick = {
                                interactionViewModel.setMessage("Đang phát triển")
                            },
                            avatarClick = { navController.navigate("avatar") },
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                    } else {
                        Spacer(modifier = Modifier.height(25.dp))
                    }

                }

                item {
                    val uniqueMovies = movieWatching.distinctBy { it.movieDTO.id }
                    RowMovieWatching(
                        moreClick = { navController.navigate("fullListMovie/watching") },
                        moviesWatching = uniqueMovies,
                        optionClick = { movieWatching ->
                            interactionViewModel.onItemClick(
                                id = movieWatching.movieDTO.id,
                                name = movieWatching.dataMovieName,
                                isWatching = true,
                                type = "watching",
                                dataMovieId = movieWatching.dataMovieId
                            )
                        },
                        detailClick = {
                            navController.navigate("episode?movieId=${it.movieDTO.id}&episodeId=${it.dataMovieId}") }
                    )
                }

                items(ProfileTitleFull.entries) { type ->
                    val dataMovieDTO = when (type) {
                        ProfileTitleFull.LIKE -> movieListLike
                        ProfileTitleFull.TRAILER -> movieListTrailer
                        ProfileTitleFull.MY_LIST -> movieListMyList
                        ProfileTitleFull.LIST_FOR_YOU -> movieListForYou
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    RowMovie(
                        title = type.nameTitle,
                        detailClick = {
                            navController.navigate("detailScreen/${it}")
                            Log.d("ProfileScreen", "ProfileScreen: $it")
                        },
                        moreClick = { navController.navigate("fullListMovie/${type.slug}") },
                        movieDTO = dataMovieDTO.take(10),
                        optionClick = { movie ->
                            interactionViewModel.onItemClick(
                                dataMovieId = "",
                                id = movie.id,
                                isWatching = false,
                                type = type.slug,
                                name = movie.name
                            )
                        },
                    )
                }
            }
            if (interactionState.isLoading) {
                LoadingBounce()
            }
        }

        if (!selectedMovie.id.isBlank()) {
            ModalBottomSheet(
                onDismissRequest = { interactionViewModel.closeSheet() },
                sheetState = sheetState
            ) {
                MovieBottomSheetContent(
                    nameMovie = selectedMovie.name,
                    onLike = {
                        interactionViewModel.postMovieToLike(selectedMovie.id)
                        interactionViewModel.closeSheet()
                    },
                    onMyList = {
                        interactionViewModel.postMovieToMyList(selectedMovie.id)
                        interactionViewModel.closeSheet()
                    },
                    onDelete = {
                        interactionViewModel.onDelete()
                        interactionViewModel.closeSheet()

                    },
                    onShare = {
                        interactionViewModel.setMessage("Đang phát triển")
                        interactionViewModel.closeSheet()
                    }
                )
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
