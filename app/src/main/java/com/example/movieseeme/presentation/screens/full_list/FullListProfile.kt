package com.example.movieseeme.presentation.screens.full_list

import CustomToast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import com.example.movieseeme.presentation.components.bottom_sheet.MovieBottomSheetContent
import com.example.movieseeme.presentation.components.movies.item.full_list.ItemFullListProfile
import com.example.movieseeme.presentation.components.movies.item.full_list.ItemFullListWatchingProfile
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullListProfile(
    navController: NavController,
    interactionViewModel: InteractionViewModel,
    slug: String
) {
    val colorBackground = MaterialTheme.colorScheme.background
    val title = ProfileTitleFull.entries.firstOrNull { it.slug == slug }?.nameTitle
    val listMovie = interactionViewModel.getMoviesListByUser(slug)

    val selectedMovie by interactionViewModel.selectedMovie.collectAsState()
    val interactionState by interactionViewModel.uiStateAction.collectAsState()
    val listMovieWatching by interactionViewModel.listMovieWatching.collectAsState()

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
        modifier = Modifier
            .fillMaxSize()
            .background(colorBackground)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 10.dp)
            .padding(bottom = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(0.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "back screen",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    text = if (!title.isNullOrEmpty()) title else "Phim đã xem",
                    style = MaterialTheme.typography.titleHeader2.copy(fontSize = 25.sp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.padding(horizontal = 10.dp)) {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    if (slug != "watching") {
                        items(listMovie) { data ->
                            ItemFullListProfile(
                                modifier = Modifier.fillMaxWidth(),
                                itemClick = { navController.navigate("detailScreen/${it}") },
                                movie = data,
                                optionClick = { movieDTO ->
                                    interactionViewModel.onItemClick(
                                        dataMovieId = "",
                                        id = movieDTO.id,
                                        isWatching = false,
                                        type = slug,
                                        name = movieDTO.name
                                    )
                                }
                            )
                        }
                    } else {
                        items(listMovieWatching) { data ->
                            ItemFullListWatchingProfile(
                                modifier = Modifier.fillMaxWidth(),
                                itemClick = {
                                    navController.navigate("episode?movieId=${it.movieDTO.id}&episodeId=${it.dataMovieId}")
                                },
                                movieWatching = data,
                                optionClick = { movieWatching ->
                                    interactionViewModel.onItemClick(
                                        id = movieWatching.movieDTO.id,
                                        name = movieWatching.dataMovieName,
                                        isWatching = true,
                                        type = "watching",
                                        dataMovieId = movieWatching.dataMovieId
                                    )
                                }
                            )
                        }
                    }

                }
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
