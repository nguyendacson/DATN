package com.example.movieseeme.presentation.screens.setting

import CustomToast
import ItemTextField
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.movie.profile.UserViewModel

@Composable
fun PasswordScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val user by userViewModel.user.collectAsState()
    val textState by userViewModel.uiTextState.collectAsState()

    val userState by userViewModel.userState.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(userState.message) {
        val msg = userState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            userViewModel.resetUserInput()
        }
    }

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
                title = "Tài Khoản và Mật khẩu",
                icon = Icons.Default.ArrowBackIosNew
            )

            Spacer(modifier = Modifier.height(30.dp))

            ItemTextField(
                placeHolder = user?.username,
                title = "Tài khoản:",
                value = textState.username,
                onChanged = { userViewModel.onUserUserNameChange(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            if (textState.isUserNameError && textState.username.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 96.dp),
                    text = "* Trên 8 kí tự",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ItemTextField(
                title = "Mật khẩu cũ:",
                value = textState.password,
                onChanged = { userViewModel.onUserPasswordChange(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            if (textState.isPasswordError) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 96.dp),
                    text = "* Trên 8 kí tự",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.sp
                    )
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            ItemTextField(
                title = "Mật khẩu mới:",
                value = textState.newPassword,
                onChanged = { userViewModel.onUserNewPasswordChange(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            if (textState.isNewPasswordError) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 96.dp),
                    text = "* Trên 8 kí tự",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            ItemTextField(
                title = "Xác nhận mật khẩu:",
                value = textState.confirmPassword,
                onChanged = { userViewModel.onUserConfirmPasswordChange(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            if (textState.isConfirmChangePasswordError) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 96.dp),
                    text = "* Mật khẩu không khớp",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.sp
                    )
                )
            }


            Spacer(modifier = Modifier.height(30.dp))

            ButtonOnBox(
                modifier = Modifier.size(180.dp, 40.dp),
                value = "Cập nhật",
                isDark = false,
                onClick = {
                    focusManager.clearFocus()

                    if (textState.isConfirmChangePasswordError || textState.isPasswordError || textState.isUserNameError) {
                        //
                    } else {
                        if (textState.username.trim() != user?.username?.trim() && textState.username.isNotEmpty()) {
                            userViewModel.updateNameAndEmailUser(
                                name = user?.name,
                                username = textState.username
                            )
                        }
                        userViewModel.changeUserPassword(
                            password = textState.password,
                            newPassword = textState.confirmPassword
                        )
                    }
                }
            )
        }
    }
    if (showToast && toastMessage.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            CustomToast(
                value = toastMessage,
                onDismiss = {
                    showToast = false
                    userViewModel.clearMessage()
                }
            )
        }
    }

    if (userState.isLoading) {
        LoadingBounce()
    }
}