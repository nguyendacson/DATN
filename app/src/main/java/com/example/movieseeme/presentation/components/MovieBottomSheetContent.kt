package com.example.movieseeme.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun MovieBottomSheetContent(
    nameMovie: String?,
    onLike: () -> Unit,
    onMyList: () -> Unit,
    onDelete: () -> Unit
) {
    Column(modifier = Modifier
        .padding(bottom = 25.dp)
        .padding(horizontal = 18.dp)) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = if (nameMovie.isNullOrEmpty()) "Tùy chọn" else nameMovie,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleHeader2.copy(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLike() }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Favorite,
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "icon like"
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Like",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMyList() }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    Icons.Default.Add,
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "icon add my list"
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "Thêm vào danh sách Yêu Thích",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLike() }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    Icons.Default.Share,
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "icon share"
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Chia sẻ",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDelete() }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Close,
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "icon delete from row"
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Xóa khỏi danh sách",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )
                )
            }
        }
    }

}
