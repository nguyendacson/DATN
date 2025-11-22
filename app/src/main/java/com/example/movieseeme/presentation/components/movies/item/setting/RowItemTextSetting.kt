package com.example.movieseeme.presentation.components.movies.item.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun RowItemTextSetting(
    modifier: Modifier,
    click: () -> Unit,
    title: String,
    title1: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { click() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleHeader2.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        )

        Text(
            text = title1,
            style = MaterialTheme.typography.titleHeader2.copy(
                fontWeight = FontWeight.Normal
            )
        )
    }
}