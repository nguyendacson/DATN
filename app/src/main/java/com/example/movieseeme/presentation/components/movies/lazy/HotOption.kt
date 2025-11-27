package com.example.movieseeme.presentation.components.movies.lazy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.enum_class.MovieHotOption
import com.example.movieseeme.presentation.components.movies.item.hot.HotItemOption
import com.example.movieseeme.presentation.viewmodels.movie.hot_new.HotViewModel


@Composable
fun HotOption(
    modifier: Modifier,
    viewModel: HotViewModel
) {
    val options = MovieHotOption.entries
    val selectedItem by viewModel.selectedHotOption.collectAsState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(options) { item ->
            val isSelected = item.key == selectedItem.key

            HotItemOption(
                modifier = modifier,
                value = item.valueOption,
                icon = item.icon,
                isChose = isSelected,
                onClick = {
                    viewModel.onOptionHotChange(item)
                }
            )
        }
    }
}