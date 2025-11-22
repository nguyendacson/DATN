package com.example.movieseeme.presentation.components.movies.item.profile.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun ContentMovie(
    modifier: Modifier = Modifier,
    movieDTO: MovieDTO,
    nameMovie: String,
    detailClick: (String) -> Unit,
    optionClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .height(55.dp)
                .clickable { detailClick(movieDTO.id) },
        ) {
            val listDirector = movieDTO.directors.joinToString(", ") { it.name }
            Text(
                text = nameMovie,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = listDirector,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleHeader2.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            )
        }
        Spacer(modifier = Modifier.width(5.dp))

        IconButton(
            onClick = { optionClick(movieDTO.id) },
            modifier = Modifier.size(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "iconMore",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
        }
    }
}