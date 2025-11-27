package com.example.movieseeme.presentation.screens.setting

import CustomToast
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
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.components.movies.item.setting.RowItemSetting
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel


@Composable
fun MenuSettingScreen(
    navController: NavController,
    interactionViewModel: InteractionViewModel
) {
    val interactionState by interactionViewModel.uiStateAction.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(interactionState.message) {
        val msg = interactionState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RowHeader(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                title = "Cài Đặt",
                icon = Icons.Default.Close
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.padding(start = 30.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = "Tài khoản",
                    modifier = Modifier.padding(top = 5.dp),
                    style = MaterialTheme.typography.titleHeader2.copy(
                        fontSize = 16.sp
                    )
                )
                RowItemSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Cài đặt chung",
                    icon = Icons.Default.Settings,
                    contentDescription = "Icon Setting",
                    click = { navController.navigate("general_setting") },
                )

                RowItemSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Chuyển đổi tài khoản",
                    icon = Icons.Default.SwitchAccount,
                    contentDescription = "Icon switchAccount",
                    click = {
                        interactionViewModel.setMessage("Đang phát triển")
                    },
                )

                RowItemSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Tài khoản của bạn",
                    icon = Icons.Default.ManageAccounts,
                    contentDescription = "Icon myAccount",
                    click = {navController.navigate("myAccount")},
                )

                RowItemSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Thông báo",
                    icon = Icons.Default.Notifications,
                    contentDescription = "Icon myAccount",
                    click = {
                        interactionViewModel.setMessage("Đang phát triển")
                    },
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 30.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = "Trợ giúp và chính sách",
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleHeader2.copy(
                        fontSize = 16.sp
                    )
                )
                RowItemSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Trợ giúp",
                    icon = Icons.AutoMirrored.Filled.Help,
                    contentDescription = "Icon help",
                    click = {
                        navController.navigate("help")
                    },
                )

                RowItemSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Gửi phản hồi",
                    icon = Icons.Default.Feedback,
                    contentDescription = "Icon feedback",
                    click = {
                        interactionViewModel.setMessage("Đang phát triển")
                    },
                )

                RowItemSetting(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Thông tin",
                    icon = Icons.Default.Info,
                    contentDescription = "Icon Info",
                    click = {
                        navController.navigate("information_app")
                    },
                )

            }
        }

        if (showToast && toastMessage.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CustomToast(
                    value = toastMessage,
                    onDismiss = {
                        showToast = false
                        interactionViewModel.clearMessage()
                    }
                )
            }
        }
    }
}