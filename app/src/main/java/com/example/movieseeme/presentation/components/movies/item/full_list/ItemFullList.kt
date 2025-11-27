package com.example.movieseeme.presentation.components.movies.item.full_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.item.IconPlay
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun ItemFullList(
    modifier: Modifier,
    movie: MovieDTO,
    isEpisode: Boolean = true,
    isIcon: Boolean = true,
    itemClick: (String) -> Unit
) {
    val bg = MaterialTheme.colorScheme.background
    val fg = MaterialTheme.colorScheme.onBackground

    val mixedColor = lerp(bg, fg, 0.1f)
    val imageUrl = movie.thumbUrl
    Box(
        modifier = modifier
            .height(80.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                shape = RoundedCornerShape(9.dp),
            )
            .background(
                color = mixedColor,
                shape = RoundedCornerShape(9.dp)
            )
            .clickable{ itemClick(movie.id) },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(125.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                if (!imageUrl.isBlank()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = "movie.name",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    LoadingBounce()
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = movie.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleHeader2.copy(fontSize = 16.sp)
                )
                if (isEpisode) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = movie.episodeCurrent.ifBlank { "Đang cập nhật" },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal)
                    )
                }
            }
            if (isIcon) {
                IconPlay(icon = Icons.Default.PlayArrow)
            } else {
                Box(modifier = Modifier.size(5.dp))
            }

        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun ShowNeo() {
    MaterialTheme() {
//        ItemFullList(isEpisode = true, modifier = Modifier.height(60.dp), itemClick = {})
    }
}