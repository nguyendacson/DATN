package com.example.movieseeme.presentation.components.movies.lazy

import ItemHomeMovie
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.model.movie.MovieDTO
import kotlin.math.abs

@Composable
fun HomeBannerPager(
    movies: List<MovieDTO>,
    pagerState: PagerState,
    clickMyList: (String) -> Unit,
    clickPlay: (String) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 42.dp),
        pageSpacing = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(430.dp),
        verticalAlignment = Alignment.Top
    ) { page ->
        val realIndex = page % movies.size
        val itemMovie = movies[realIndex]

        val pageOffset =
            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        val scale = 1f - abs(pageOffset) * 0.2f  // thu nhỏ dần item lệch trung tâm
        val alpha = 1f - abs(pageOffset) * 0.5f   // làm mờ item lệch tâm

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
                .fillMaxSize()

        ) {
            ItemHomeMovie(
                modifier = Modifier.fillMaxSize(),
                myListClick = { clickMyList(itemMovie.id) },
                playClick = { clickPlay(itemMovie.id) },
                movie = itemMovie
            )
        }
    }
}