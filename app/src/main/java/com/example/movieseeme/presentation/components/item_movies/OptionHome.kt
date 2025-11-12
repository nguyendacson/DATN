package com.example.movieseeme.presentation.components.item_movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieseeme.presentation.components.CustomButtonItem


@Composable
fun OptionHome(modifier: Modifier, click: () -> Unit) {
    val options = listOf("Tất cả", "Phim mới", "Phim hot", "Đang chiếu", "Sắp chiếu")
    var selectedItem by remember { mutableStateOf(options.first()) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(options) { item ->
            val isSelected = item == selectedItem

            CustomButtonItem(
                modifier = Modifier.size(width = 90.dp, height = 28.dp),
                value = item,
                isChose = isSelected,
                onClick = {
                    selectedItem = item
                    click()
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun OptionHomePreview() {
    OptionHome(modifier = Modifier, click = {})
}