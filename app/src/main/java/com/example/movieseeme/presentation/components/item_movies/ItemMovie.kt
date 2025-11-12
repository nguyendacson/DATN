package com.example.movieseeme.presentation.components.item_movies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.presentation.theme.extension.titleHeader

@Composable
fun ItemMovie(
    modifier: Modifier,
//    movie: Movie,
    imageUrl: String,
    playClick: () -> Unit,
    myListClick: () -> Unit
) {
    Box(
        modifier = Modifier
//            .background(Color.LightGray)
            .size(290.dp, 410.dp)
            .clip(RoundedCornerShape(9.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = "movie.name",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Movie.name",
                style = MaterialTheme.typography.titleHeader.copy(fontSize = 22.sp)
            )
            Spacer(modifier = Modifier.size(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ItemMainBotton(
                    modifier = Modifier.size(
                        110.dp,
                        33.dp
                    ),
                    value = "Play",
                    isPlay = true,
                    itemIcon = Icons.Default.PlayArrow,
                    contentIcon = "Play",
                    onClick = playClick,
                )

                ItemMainBotton(
                    modifier = Modifier.size(
                        110.dp,
                        33.dp
                    ),
                    value = "My List",
                    isPlay = false,
                    itemIcon = Icons.Default.Add,
                    contentIcon = "Add my list",
                    onClick = myListClick,
                )
            }
        }
    }
}

@Composable
fun ItemMainBotton(
    modifier: Modifier = Modifier,
    value: String,
    isPlay: Boolean = false,
    itemIcon: ImageVector,
    contentIcon: String,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(10.dp)
    val colorBackground = if (isPlay) Color.White else Color.LightGray
    val colorTitle = if (isPlay) Color.Black else Color.White

    Box(
        modifier = modifier
            .clip(shape)
            .border(
                BorderStroke(1.dp, colorBackground.copy(alpha = 0.5f)),
                shape = shape
            )
    ) {
        Button(
            modifier = Modifier.matchParentSize(),
            shape = shape,
            colors = ButtonDefaults.buttonColors(colorBackground),
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 5.dp)
        ) {
            Icon(
                imageVector = itemIcon,
                contentDescription = contentIcon,
                modifier = Modifier.size(20.dp),
                tint = colorTitle
            )
            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = value,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = colorTitle
                )
            )
        }
    }
}

@Composable
fun ItemBotton(
    modifier: Modifier = Modifier,
    imageUrl: String,
    itemClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(9.dp))
            .clickable { itemClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = "movie.name",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable

fun View() {
    MaterialTheme() {
        ItemMovie(modifier = Modifier, playClick = {}, myListClick = {}, imageUrl = "dsad")
    }
}