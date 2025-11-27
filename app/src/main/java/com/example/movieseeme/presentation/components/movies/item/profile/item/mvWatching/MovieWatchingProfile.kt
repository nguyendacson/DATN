package com.example.movieseeme.presentation.components.movies.item.profile.item.mvWatching

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.model.movie.MovieWatching
import com.example.movieseeme.presentation.components.movies.item.profile.item.ContentMovie

@Composable
fun MovieWatchingProfile(
    movieWatching: MovieWatching,
    optionClick: (MovieWatching) -> Unit,
    detailClick: (MovieWatching) -> Unit,
) {
    val movieDTO = movieWatching.movieDTO

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.width(155.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ImageMovieWatching(
                modifier = Modifier.fillMaxSize(),
                movieWatching = movieWatching,
                progressSeconds = movieWatching.progressSeconds,
                detailClick = { detailClick(movieWatching) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ContentMovie(
                modifier = Modifier.fillMaxWidth(),
                movieDTO = movieDTO,
                nameMovie = movieWatching.dataMovieName,
                detailClick = { detailClick(movieWatching) },
                optionClick = { optionClick(movieWatching) }
            )
        }
    }
}
