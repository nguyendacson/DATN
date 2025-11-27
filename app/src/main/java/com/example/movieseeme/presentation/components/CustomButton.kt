package com.example.movieseeme.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieseeme.presentation.theme.extension.titleHeader1

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    value: String,
    imageVector: ImageVector? = null,
    isImageVector: Boolean = false,
    onClick: () -> Unit,
    isBold: Boolean = true,
    icon: Boolean = false,
    contentIcon: String? = null,
    itemIcon: Int? = null
) {
    val shape = RoundedCornerShape(6.dp)
    val colorOnBackground = MaterialTheme.colorScheme.onBackground
    val colorBackground = MaterialTheme.colorScheme.background
    Box(
        modifier = modifier
            .clip(shape)
            .border(
                BorderStroke(1.dp, colorBackground),
                shape = shape
            )
    ) {
        Button(
            modifier = Modifier.matchParentSize(),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorOnBackground,
            ),
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 5.dp)
        ) {
            if (icon && itemIcon != null) {
                if (isImageVector) {
                    Icon(
                        imageVector = imageVector!!,
                        contentDescription = contentIcon,
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.background
                    )
                } else {
                    Icon(
                        painter = painterResource(id = itemIcon),
                        contentDescription = contentIcon,
                        modifier = Modifier.size(30.dp),
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = value,
                style = if (isBold) MaterialTheme.typography.titleHeader1.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ) else MaterialTheme.typography.titleHeader1.copy(fontSize = 15.sp)
            )
        }
    }
}

@Composable
fun TextErrorInput(modifier: Modifier, title: String) {
    Text(
        text = title,
        modifier = modifier
            .padding(start = 5.dp),
        style = MaterialTheme.typography.titleHeader1.copy(
            color = Color.Red,
            fontSize = 10.sp
        )
    )
}

