package com.example.movieseeme.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.presentation.theme.extension.titleHeader


@Composable
fun HeaderScreen(
    modifier: Modifier,
    search: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SEEME",
                style = MaterialTheme.typography.titleHeader.copy(
                    fontSize = 25.sp,
                    color = Color.Red,
                    letterSpacing = 2.sp
                )
            )
            IconButton(onClick = search) {
                Icon(
                    Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    contentDescription = "search Movie",
                )
            }
        }
    }
}