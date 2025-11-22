package com.example.movieseeme.presentation.components.movies.item.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.presentation.components.LoadingBounce

@Composable
fun ItemImageHome(
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
}