package com.example.movieseeme.presentation.components.movies.item.full_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.model.movie.MovieWatching
import com.example.movieseeme.presentation.components.movies.item.profile.item.ContentMovie
import com.example.movieseeme.presentation.components.movies.item.profile.item.mvWatching.ImageMovieWatching

@Composable
fun ItemFullListWatchingProfile(
    modifier: Modifier,
    movieWatching: MovieWatching,
    itemClick: (MovieWatching) -> Unit,
    optionClick: (MovieWatching) -> Unit

) {
    Box(
        modifier = modifier
            .height(85.dp)
            .clickable { itemClick(movieWatching) },
        contentAlignment = Alignment.Center
    ) {
        val movieDTO = movieWatching.movieDTO
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            ImageMovieWatching(
                modifier = Modifier.fillMaxHeight(),
                movieWatching = movieWatching,
                progressSeconds = movieWatching.progressSeconds,
                detailClick = { itemClick(movieWatching) }
            )

            Box(modifier = Modifier
                .weight(1f)
                .padding(top = 5.dp)) {
                ContentMovie(
                    modifier = Modifier,
                    movieDTO = movieDTO,
                    nameMovie = movieWatching.dataMovieName,
                    detailClick = { itemClick(movieWatching) },
                    optionClick = { optionClick(movieWatching) }
                )
            }
        }
    }
}
