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
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.movies.item.profile.item.ContentMovie
import com.example.movieseeme.presentation.components.movies.item.profile.item.mv.ImageMovie

@Composable
fun ItemFullListProfile(
    modifier: Modifier,
    movie: MovieDTO,
    itemClick: (String) -> Unit,
    optionClick: (MovieDTO) -> Unit

) {
    Box(
        modifier = modifier
            .height(85.dp)
            .clickable { itemClick(movie.id) },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            ImageMovie(
                modifier = Modifier.fillMaxHeight(),
                movieDTO = movie,
                detailClick = { itemClick(movie.id) }
            )

            Box(modifier = Modifier
                .weight(1f)
                .padding(top = 5.dp)) {
                ContentMovie(
                    modifier = Modifier,
                    movieDTO = movie,
                    nameMovie = movie.name,
                    detailClick = { itemClick(movie.id) },
                    optionClick = { optionClick(movie) }
                )
            }
        }
    }
}
