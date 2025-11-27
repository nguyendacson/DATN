package com.example.movieseeme.presentation.screens.full_list

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.domain.enum_class.HomeTitleFull
import com.example.movieseeme.presentation.components.movies.lazy.ColumItemFull
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.movie.home.HomeViewModel

@Composable
fun FullMovieScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    category: String
) {
    val colorBackground = MaterialTheme.colorScheme.background

    val title = HomeTitleFull.entries.firstOrNull { it.slug == category }?.nameTitle
    val listMovie = viewModel.getMoviesListByCategory(category)

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
                    text = if (!title.isNullOrEmpty()) title else "Movie",
                    style = MaterialTheme.typography.titleHeader2.copy(fontSize = 25.sp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.padding(horizontal = 10.dp)) {
                ColumItemFull(
                    movies = listMovie,
                    modifier = Modifier,
                    isIcon = true,
                    onClick = { navController.navigate("detailScreen/${it}") },
                    isEpisode = true
                )
            }
        }
    }
}
