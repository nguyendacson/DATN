package com.example.movieseeme.presentation.components.movies.item.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.domain.model.user.InformationUser
import com.example.movieseeme.presentation.theme.extension.orange
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun HeaderProfile(
    modifier: Modifier,
    isDowload: Boolean = true,
    userInfo: InformationUser,
    downloadClick: () -> Unit,
    avatarClick: () -> Unit,
) {
    Box(modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ImageAvatar(modifier = Modifier, avatarClick = avatarClick, userInfo = userInfo)

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = userInfo.name?.takeIf { it.isNotBlank() } ?: "Tên người dùng",
                style = MaterialTheme.typography.titleHeader2.copy(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            if (isDowload){
                RowDownload(modifier = Modifier, downloadClick = downloadClick)
            }

        }

    }
}

@Composable
fun ImageAvatar(
    userInfo: InformationUser,
    avatarClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(50.dp)
            )
            .clip(RoundedCornerShape(50.dp))
            .clickable(onClick = avatarClick)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(model = userInfo.avatar),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun RowDownload(
    modifier: Modifier,
    downloadClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { downloadClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.orange),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Download,
                contentDescription = "icon download",
                modifier = Modifier.size(24.dp),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Download",
            style = MaterialTheme.typography.titleHeader2.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp
            )
        )
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "icon download",
                modifier = Modifier.size(33.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
//
//
//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun HeaderProfilePreview() {
//    MaterialTheme {
//        HeaderProfile(
//            modifier = Modifier,
//            text = "Nguyen dac son",
//            downloadClick = {},
//            avatarClick = {},
//            imageUrl = ""
//        )
//    }
//}