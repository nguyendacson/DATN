package com.example.movieseeme.presentation.components.movies.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconPlay(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .border(
                width = 1.5.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = "play"
        )
    }
}