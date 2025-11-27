package com.example.movieseeme.presentation.components.movies.item.hot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.item.ItemOnPager
import com.example.movieseeme.presentation.theme.extension.titleHeader
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun ItemHotPage(
    modifier: Modifier,
    textNumber: String?,
    movie: MovieDTO,
    playClick: (String) -> Unit,
    myListClick: (String) -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (tvNumber, tvBoxBody) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .border(
                    1.5.dp, Color.Gray, RoundedCornerShape(
                        topStart = 9.dp,
                        bottomStart = 9.dp,
                        bottomEnd = 9.dp
                    )
                )
                .constrainAs(tvBoxBody) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 9.dp,
                            bottomStart = 9.dp,
                            bottomEnd = 9.dp
                        )
                    )
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background.copy(alpha = 0.90f),
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                            )
                        )
                    )
                    .fillMaxSize()
                    .padding(top = 30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .border(
                            1.dp, Color.Gray, RoundedCornerShape(
                                topStart = 9.dp,
                                bottomStart = 9.dp,
                                bottomEnd = 9.dp

                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (movie.thumbUrl.isNotEmpty()) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 9.dp,
                                        bottomStart = 9.dp,
                                        bottomEnd = 9.dp
                                    )
                                ),
                            painter = rememberAsyncImagePainter(model = movie.thumbUrl),
                            contentDescription = "Therm Url",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        LoadingBounce()
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 8.dp),
//                        .padding(bottom =  ),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = movie.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleHeader2.copy(fontSize = 20.sp)
                    )

                    Text(
                        text = movie.content,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleHeader2.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        ItemOnPager(
                            modifier = Modifier.size(
                                100.dp,
                                30.dp,
                            ),
                            value = "Xem",
                            isPlay = true,
                            itemIcon = Icons.Default.PlayArrow,
                            contentIcon = "xem",
                            shape = 8,
                            onClick = {playClick(movie.id)},
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        ItemOnPager(
                            modifier = Modifier.size(
                                100.dp,
                                30.dp
                            ),
                            value = "Yêu Thích",
                            isPlay = false,
                            itemIcon = Icons.Default.Add,
                            contentIcon = "yêu thích",
                            shape = 8,
                            onClick = { myListClick(movie.id) },
                        )
                    }
                }

            }
        }
        if (textNumber != null) {
            Text(
                text = textNumber,
                style = MaterialTheme.typography.titleHeader.copy(fontSize = 45.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .constrainAs(tvNumber) {
                        top.linkTo(parent.top, margin = (-23).dp)
                        start.linkTo(parent.start)
                    }
            )
        }
    }
}


//@Preview(showSystemUi = true)
//@Composable
//
//fun ViewShow() {
//    MaterialTheme() {
//        ItemHotPage(
//            modifier = Modifier,
//            playClick = {},
//            myListClick = {},
//            textNumber = "01",
//            movie = MovieDTO("1","1","23","nm","name"),
////            nameMovie = "dsad",
////            reviewMovie = "dsad"
//        )
//    }
//}