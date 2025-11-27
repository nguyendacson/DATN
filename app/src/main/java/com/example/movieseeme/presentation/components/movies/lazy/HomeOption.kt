package com.example.movieseeme.presentation.components.movies.lazy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieseeme.domain.enum_class.MovieHomeOption
import com.example.movieseeme.presentation.components.movies.item.home.ItemOnHomeOption
import com.example.movieseeme.presentation.viewmodels.movie.home.HomeViewModel


@Composable
fun HomeOption(
    modifier: Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val options = MovieHomeOption.entries
    val selectedItem by homeViewModel.selectedOption.collectAsState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(options) { item ->
            val isSelected = item.slug == selectedItem.slug

            ItemOnHomeOption(
                modifier = Modifier.size(90.dp, 30.dp),
                value = item.nameOption,
                isChose = isSelected,
                option = item,
                onClick = {
                    homeViewModel.onOptionHomeChange(item)
                    if (item == MovieHomeOption.THEM) {
                        navController.navigate("optionCategory")
                    } else {
                        homeViewModel.onTypeChange(item.slug)
                        homeViewModel.onCategoryChange("")
                    }
                }
            )
        }
    }
}