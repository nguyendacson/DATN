package com.example.movieseeme.presentation.components.movies.item.profile.item.mv

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun ImageMovie(
    modifier: Modifier,
    movieDTO: MovieDTO,
    detailClick: (String) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        val duration = movieDTO.time.replace(" ", "")
        val minutesPerEpisode = duration.filter { it.isDigit() }.toInt()
        val h = minutesPerEpisode / 60
        val m = minutesPerEpisode % 60
        val textTime =  "%dh%02dm".format(h, m)

        ConstraintLayout(
            modifier = Modifier
                .size(155.dp, 85.dp)
        ) {
            val (container, time) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { detailClick(movieDTO.id) }
                    .constrainAs(container) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {

                // Ảnh
                if (movieDTO.thumbUrl.isNotBlank()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(model = movieDTO.thumbUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                } else {
                    LoadingBounce()
                }

            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.DarkGray.copy(8.7f))
                    .padding(3.dp)
                    .constrainAs(time) {
                        bottom.linkTo(parent.bottom, margin = 6.dp)
                        end.linkTo(parent.end, margin = 6.dp)
                    },
            ) {
                Text(
                    text = textTime,
                    style = MaterialTheme.typography.titleHeader2.copy(
                        fontSize = 12.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}