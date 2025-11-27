package com.example.movieseeme.presentation.screens.setting


import ItemCheck
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieseeme.data.remote.model.systemTheme.ThemeMode
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.viewmodels.auth.ThemeViewModel
import kotlinx.coroutines.launch

@Composable
fun ThemeSetScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel,
) {
    val themeMode by themeViewModel.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
    val scope = rememberCoroutineScope()

    val onThemeChange = { mode: ThemeMode ->
        scope.launch {
            themeViewModel.setTheme(mode)
        }
    }

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
                title = "Giao Diện",
                icon = Icons.Default.ArrowBackIosNew
            )
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
                    .padding(start = 5.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                ItemCheck(
                    text = "Theo hệ thống",
                    isChecked = themeMode == ThemeMode.SYSTEM,
                    onCheckedChange = { onThemeChange(ThemeMode.SYSTEM) }
                )

                ItemCheck(
                    text = "Giao diện sáng",
                    isChecked = themeMode == ThemeMode.LIGHT,
                    onCheckedChange = { onThemeChange(ThemeMode.LIGHT) }
                )

                ItemCheck(
                    text = "Giao diện tối ",
                    isChecked = themeMode == ThemeMode.DARK,
                    onCheckedChange = { onThemeChange(ThemeMode.DARK) }
                )
            }
        }
    }
}

