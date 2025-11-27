@file:JvmName("ItemHomePagerKt")

package com.example.movieseeme.presentation.components.movies.item.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieseeme.domain.enum_class.MovieHomeOption
import com.example.movieseeme.presentation.theme.extension.titleHeader1

@Composable
fun ItemOnHomeOption(
    modifier: Modifier = Modifier,
    value: String,
    option: MovieHomeOption,
    onClick: () -> Unit,
    isChose: Boolean = true,
) {
    val shape = RoundedCornerShape(40.dp)
    val colorOnBackground = MaterialTheme.colorScheme.onBackground
    val colorBackground = MaterialTheme.colorScheme.background
    Box(
        modifier = modifier
            .clip(shape)
            .border(
                BorderStroke(
                    1.dp,
                    color = colorOnBackground
                ),
                shape = shape
            )
    ) {
        Button(
            modifier = Modifier.matchParentSize(),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                if (isChose) colorOnBackground else colorBackground,
            ),
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleHeader1.copy(
                        fontSize = 12.sp,
                        color = if (isChose) colorBackground else colorOnBackground
                    )
                )
                if (option == MovieHomeOption.THEM) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        Icons.Default.ExpandMore,
                        tint = if (isChose) colorBackground else colorOnBackground,
                        contentDescription = "icon more"
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ViewLayout() {
    MaterialTheme() {
        ItemOnHomeOption(
            value = "Xem Thêm",
            onClick = {},
            isChose = true,
            modifier = Modifier.size(100.dp, 30.dp),
            option = MovieHomeOption.THEM
        )
    }
}