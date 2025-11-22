package com.example.movieseeme.presentation.components.movies.lazy

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.movies.item.hot.ItemHotPage

@SuppressLint("DefaultLocale")
@Composable
fun HotBannerPager(
    modifier: Modifier,
    movies: List<MovieDTO>,
    myListClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(28.dp),
        contentPadding = PaddingValues(top = 23.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
    ) {
        itemsIndexed(movies) { index, item ->
            val number = String.format("%02d", index + 1)
            ItemHotPage(
                modifier = Modifier.fillMaxSize(),
                textNumber = number,
                movie = item,
                playClick = {},
                myListClick = {myListClick(item.id)}
            )
        }
    }
}