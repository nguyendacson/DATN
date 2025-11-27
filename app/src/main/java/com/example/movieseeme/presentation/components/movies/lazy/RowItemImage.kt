package com.example.movieseeme.presentation.components.movies.lazy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.item.home.ItemImageHome
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun RowItemImage(
    value: String,
    isMore: Boolean? = true,
    moreClick: () -> Unit,
    onClick:(String) -> Unit,
    movies: List<MovieDTO>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleHeader2
        )
        if (isMore == true){
            Text(
                text = "Xem thêm",
                style = MaterialTheme.typography.titleHeader2,
                modifier = Modifier.clickable { moreClick() })
        }
    }

    Spacer(modifier = Modifier.height(5.dp))

    if (!movies.isEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            items(movies) { data ->
                if (!data.posterUrl.isBlank()) {
                    ItemImageHome(
                        modifier = Modifier.size(100.dp, 150.dp),
                        itemClick = {onClick(data.id)},
                        imageUrl = data.posterUrl
                    )
                } else {
                    LoadingBounce(
                        modifier = Modifier
                            .size(100.dp, 150.dp)
                            .clip(RoundedCornerShape(9.dp))
                    )
                }

            }
        }
    } else {
        LoadingBounce(
            modifier = Modifier
                .padding(start = 18.dp)
                .size(100.dp, 150.dp)
                .clip(RoundedCornerShape(9.dp))
        )
    }

}