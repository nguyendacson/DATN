package com.example.movieseeme.presentation.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.components.movies.item.profile.HeaderProfile
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.user.AuthViewModel
import com.example.movieseeme.presentation.viewmodels.user.UserViewModel

@Composable
fun MyAccountScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    rootNavController: NavController
) {
    var isShow by remember { mutableStateOf(false) }
    val userInfo by userViewModel.user.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RowHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                navController = navController,
                title = "Tài Khoản",
                icon = Icons.Default.ArrowBackIosNew
            )

            Box(
                modifier =
                    Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(modifier = Modifier) {
                            if (userInfo != null) {
                                HeaderProfile(
                                    modifier = Modifier,
                                    userInfo = userInfo!!,
                                    downloadClick = {},
                                    avatarClick = { navController.navigate("avatar") },
                                    isDowload = false
                                )
                            } else {
                                Text(
                                    text = "Loading...",
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                    }
                    Box(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp))
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.95f))
                                .padding(vertical = 30.dp, horizontal = 20.dp),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                ContentOnBox(
                                    title = "Ảnh đại diện",
                                    onClick = { navController.navigate("chose_avatar") }
                                )
                                ContentOnBox(
                                    title = "Tên",
                                    onClick = { navController.navigate("nameScreen") }
                                )
                                ContentOnBox(
                                    title = "Email",
                                    onClick = { navController.navigate("emailScreen") }
                                )
                                ContentOnBox(
                                    title = "Tài khoản và Mật khẩu",
                                    onClick = { navController.navigate("userAndPasswordScreen") }
                                )

                                ButtonOnBox(
                                    modifier = Modifier.size(190.dp, 45.dp),
                                    value = "Đăng Xuất",
                                    isDark = true,
                                    onClick = { isShow = true }
                                )
                            }
                        }

                    }
                }

            }

        }
    }

    if (isShow) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
                .clickable { isShow = false },
            contentAlignment = Alignment.Center
        )
        {
            Box(
                modifier = Modifier
                    .size(280.dp, 200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.DarkGray)
                    .clickable(enabled = false) {}
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bạn chắc chắn muốn Đăng Xuất ?",
                        style = MaterialTheme.typography.titleHeader2.copy(
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ButtonOnBox(
                            "Không",
                            isDark = false,
                            onClick = { isShow = false },
                            modifier = Modifier.size(110.dp, 40.dp)
                        )
                        ButtonOnBox(
                            "Có",
                            isDark = true,
                            onClick = {
                                authViewModel.clearToken()
                                userViewModel.onLogoutClicked()
                                rootNavController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }

                            },
                            modifier = Modifier.size(110.dp, 40.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ContentOnBox(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleHeader2.copy(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.background
            )
        )
        Icon(
            Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "connect icon",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ButtonOnBox(
    value: String,
    isDark: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(50.dp))
            .background(if (isDark) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleHeader2
                .copy(
                    fontSize = 15.sp,
                    color = if (isDark) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
                )
        )
    }
}

