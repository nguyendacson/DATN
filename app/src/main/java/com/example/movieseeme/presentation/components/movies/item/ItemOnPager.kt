package com.example.movieseeme.presentation.components.movies.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemOnPager(
    modifier: Modifier = Modifier,
    value: String,
    shape: Int,
    isPlay: Boolean = false,
    itemIcon: ImageVector,
    contentIcon: String,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(shape.dp)
    val colorBackground = if (isPlay) Color.White else Color.LightGray
    val colorTitle = if (isPlay) Color.Black else Color.White

    Box(
        modifier = modifier
            .clip(shape)
            .border(
                BorderStroke(1.dp, colorBackground.copy(alpha = 0.5f)),
                shape = shape
            )
    ) {
        Button(
            modifier = Modifier.matchParentSize(),
            shape = shape,
            colors = ButtonDefaults.buttonColors(colorBackground),
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 5.dp)
        ) {
            Icon(
                imageVector = itemIcon,
                contentDescription = contentIcon,
                modifier = Modifier.size(20.dp),
                tint = colorTitle
            )
            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = value,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = colorTitle
                )
            )
        }
    }
}