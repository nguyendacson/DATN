package com.example.movieseeme.presentation.components.movies.item.hot

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieseeme.R
import com.example.movieseeme.presentation.theme.extension.titleHeader1

@Composable
fun HotItemOption(
    modifier: Modifier,
    onClick: () -> Unit,
    value: String,
    icon: Int,
    isChose: Boolean = true
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
                    colorOnBackground
                ),
                        shape = shape
            )
    ) {
        Button(
            modifier = Modifier.matchParentSize(),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                if (isChose) colorOnBackground else colorBackground
            ),
            onClick = onClick
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleHeader1.copy(
                        fontSize = 12.sp,
                        color = if (isChose) colorBackground else colorOnBackground
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ViewLayoutHot() {
    MaterialTheme() {
        HotItemOption(
            value = "Phim Hot",
            onClick = {},
            icon = R.drawable.icon_hot,
            modifier = Modifier.size(150.dp,30.dp),
        )
    }
}