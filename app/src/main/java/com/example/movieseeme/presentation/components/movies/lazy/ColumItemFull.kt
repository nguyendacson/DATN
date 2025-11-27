package com.example.movieseeme.presentation.components.movies.lazy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.movies.item.full_list.ItemFullList

@Composable
fun ColumItemFull(
    modifier: Modifier,
    movies: List<MovieDTO>,
    isEpisode: Boolean,
    onClick:(String)-> Unit,
    isIcon: Boolean
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(movies) { data ->
            ItemFullList(
                modifier = Modifier.fillMaxWidth(),
                isEpisode = isEpisode,
                isIcon = isIcon,
                itemClick = {onClick(it)},
                movie = data,
            )
        }
    }
}