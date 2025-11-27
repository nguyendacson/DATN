package com.example.movieseeme.domain.enum_class.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector

enum class MovieDetailOption(val icon: ImageVector, val title: String) {
    LIKE(Icons.Filled.Favorite, "Like"),
    SHARE(Icons.Filled.Share, "Chia sẻ"),
    ADD(Icons.Filled.Add, "Yêu thích"),
    DOWNLOAD(Icons.Filled.Download, "Tải xuống")
}