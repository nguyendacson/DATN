package com.example.movieseeme.presentation.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.presentation.components.lock_screen.LockScreenOrientationPortrait
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.theme.extension.orange
import com.example.movieseeme.presentation.theme.extension.titleHeader
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun InformationAppScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 10.dp)
            .padding(bottom = 15.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            RowHeader(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                title = "Thông tin ứng dụng",
                icon = Icons.Default.ArrowBackIosNew
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Ứng dụng xem phim trực tuyến Moie SeeMe !",
                    style = MaterialTheme.typography.titleHeader.copy(fontSize = 25.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.padding(start = 3.dp),
                    text = "version: 1.0.0",
                    style = MaterialTheme.typography.titleHeader2.copy(color = MaterialTheme.colorScheme.orange)
                )
            }

        }
    }
}