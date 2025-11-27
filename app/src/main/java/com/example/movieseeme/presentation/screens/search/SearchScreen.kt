package com.example.movieseeme.presentation.screens.search

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.presentation.components.movies.lazy.ColumItemFull
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import com.example.movieseeme.presentation.viewmodels.search.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    interactionViewModel: InteractionViewModel,
    searchViewModel: SearchViewModel
) {

    var isFocused by remember { mutableStateOf(false) }
    val uiState by searchViewModel.keySearch.collectAsState()

    val movieSearch by searchViewModel.listMovieSearch.collectAsState()
    val movieListFYou by interactionViewModel.listMovieListForYou.collectAsState()
    val focusManager = LocalFocusManager.current
    val colorBackground = MaterialTheme.colorScheme.background
    val colorOnBackground = MaterialTheme.colorScheme.onBackground
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

                OutlinedTextField(
                    value = uiState,
                    onValueChange = searchViewModel::onKeySearchChange,
                    textStyle = MaterialTheme.typography.titleHeader2,
                    placeholder = {
                        if (!isFocused && uiState.isEmpty()) {
                            Text(
                                text = "Tìm kiếm tên phim...",
                                style = MaterialTheme.typography.titleHeader2.copy(
                                    color = colorOnBackground.copy(alpha = 0.6f)
                                )
                            )
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                            contentDescription = "Hind password visibility"
                        )
                    },
                    trailingIcon = {
                        if (uiState.isNotEmpty()) {
                            IconButton(onClick = {
                                searchViewModel.onKeySearchChange("")
                                searchViewModel.clearSearchResult()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear text",
                                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            searchViewModel.getMoviesSearch()
                        }
                    ),
                    modifier = Modifier
                        .height(47.dp)
                        .weight(1f)
                        .onFocusChanged {
                            isFocused = it.isFocused
                        }
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(modifier = Modifier.padding(horizontal = 10.dp)) {
                if (movieSearch.isEmpty()) {
                    if (movieListFYou.isEmpty()) {
                        interactionViewModel.getMoviesForYou()
                    } else {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Phim dành riêng cho bạn",
                                style = MaterialTheme.typography.titleHeader2.copy(fontSize = 14.sp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ColumItemFull(
                                movies = movieListFYou.take(10),
                                modifier = Modifier,
                                isIcon = true,
                                onClick = {navController.navigate("detailScreen/${it}")},
                                isEpisode = false
                            )
                        }
                    }

                } else {
                    ColumItemFull(
                        movies = movieSearch,
                        modifier = Modifier,
                        isIcon = true,
                        onClick = {navController.navigate("detailScreen/${it}")},
                        isEpisode = false
                    )
                }
            }
        }
    }
}
