package com.example.movieseeme.presentation.components.movies.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun RowHeader(
    modifier: Modifier,
    navController: NavController,
    title: String,
    icon: ImageVector
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(0.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "back screen",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleHeader2.copy(fontSize = 20.sp)
        )
    }
}