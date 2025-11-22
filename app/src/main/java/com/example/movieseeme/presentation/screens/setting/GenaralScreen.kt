package com.example.movieseeme.presentation.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieseeme.data.remote.model.systemTheme.ThemeMode
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.components.movies.item.setting.RowItemTextSetting
import com.example.movieseeme.presentation.viewmodels.user.ThemeViewModel

@Composable
fun GeneralScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel,
) {
    val themeMode by themeViewModel.themeMode.collectAsState(initial = ThemeMode.SYSTEM)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 10.dp)
            .padding(bottom = 15.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RowHeader(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                title = "Cài Đặt Chung",
                icon = Icons.Default.ArrowBackIosNew
            )
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
                    .padding(start = 5.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                RowItemTextSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Ngôn ngữ",
                    title1 = "Tiếng Việt",
                    click = {},
                )

                RowItemTextSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Quốc gia",
                    title1 = "Việt Nam",
                    click = {},
                )

                RowItemTextSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Giao diện",
                    title1 = when (themeMode) {
                        ThemeMode.LIGHT -> "Sáng"
                        ThemeMode.DARK -> "Tối"
                        ThemeMode.SYSTEM -> "Hệ Thống"
                    },
                    click = { navController.navigate("themeSet") },
                )

            }
        }
    }
}