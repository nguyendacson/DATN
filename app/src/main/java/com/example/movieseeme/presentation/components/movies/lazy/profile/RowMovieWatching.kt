package com.example.movieseeme.presentation.components.movies.lazy.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieseeme.domain.model.movie.MovieWatching
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.item.profile.item.mvWatching.MovieWatchingProfile
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun RowMovieWatching(
    moreClick: () -> Unit,
    moviesWatching: List<MovieWatching>,
    optionClick: (MovieWatching) -> Unit,
    detailClick: (String) -> Unit
) {
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Đã xem",
                style = MaterialTheme.typography.titleHeader2.copy(fontSize = 15.sp)
            )

            Text(
                text = "Xem tất cả",
                style = MaterialTheme.typography.titleHeader2.copy(fontSize = 15.sp),
                modifier = Modifier.clickable { moreClick() })
        }

        Spacer(modifier = Modifier.height(5.dp))

        if (moviesWatching.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(13.dp),
            ) {
                items(
                    moviesWatching,
                    key = { it.id }) { data ->
                    MovieWatchingProfile(
                        movieWatching = data,
                        optionClick = { optionClick(data) },
                        detailClick = { detailClick(data.id) }
                    )
                }
            }
        } else {
            LoadingBounce(
                modifier = Modifier
                    .size(155.dp, 85.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }

}