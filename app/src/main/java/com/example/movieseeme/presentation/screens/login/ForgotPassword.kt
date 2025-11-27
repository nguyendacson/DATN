package com.example.movieseeme.presentation.screens.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieseeme.data.remote.model.request.auth.forgot_password.ResetPassRequest
import com.example.movieseeme.presentation.components.CustomButton
import com.example.movieseeme.presentation.components.TextErrorInput
import com.example.movieseeme.presentation.components.user.ShadowTextField
import com.example.movieseeme.presentation.theme.extension.titleHeader
import com.example.movieseeme.presentation.viewmodels.auth.AuthViewModel


@Composable
fun ForgotPassword(
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit = {}
) {
    BackHandler(enabled = true) { authViewModel.setShowForgotPassword(false) }
    val uiState by authViewModel.uiState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
                .padding(horizontal = 20.dp)
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center

        ) {
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f))
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (uiState.sendEmail) {
                    InputNewPassword(authViewModel = authViewModel)
                } else {
                    SendEmail(authViewModel = authViewModel)
                }
            }
        }

    }
}

@Composable
fun SendEmail(authViewModel: AuthViewModel) {
    val uiState by authViewModel.uiState.collectAsState()

    Text(
        text = "Nhập email để lấy lại mật khẩu",
        style = MaterialTheme.typography.titleHeader.copy(fontSize = 18.sp)
    )

    Spacer(modifier = Modifier.size(16.dp))

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        ShadowTextField(
            value = uiState.email,
            onValueChange = authViewModel::onEmailChange,
            icon = false,
            placeholder = "Email",
            isError = uiState.isEmailError
        )
        if (uiState.isEmailError) {
            Spacer(modifier = Modifier.height(5.dp))
            TextErrorInput(
                modifier = Modifier.align(Alignment.Start),
                title = "* Sai định dạng"
            )
        }
    }

    Spacer(modifier = Modifier.size(16.dp))

    CustomButton(
        modifier = Modifier.size(width = 180.dp, height = 40.dp),
        value = "Xác nhận",
        onClick = {
            authViewModel.forgotPassword(uiState.email)
        },
        icon = false,
        isBold = true,
        contentIcon = "Send Email",
    )
}

@Composable
fun InputNewPassword(authViewModel: AuthViewModel) {
    val uiState by authViewModel.uiState.collectAsState()
    BackHandler(enabled = true) { authViewModel.setSendEmail(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { authViewModel.setSendEmail(false) },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Nhập mã và mật khẩu mới",
            style = MaterialTheme.typography.titleHeader.copy(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        ShadowTextField(
            value = uiState.keyResetPass,
            onValueChange = authViewModel::onKeyResetPassChange,
            icon = false,
            placeholder = "Mã xác nhận",
            isError = false
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            ShadowTextField(
                value = uiState.password,
                onValueChange = authViewModel::onPasswordChange,
                icon = true,
                placeholder = "Mật khẩu",
                isError = uiState.isPasswordError
            )
            if (uiState.isPasswordError) {
                Spacer(modifier = Modifier.height(5.dp))
                TextErrorInput(
                    modifier = Modifier.align(Alignment.Start),
                    title = "* Hơn 8 kí tự"
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        CustomButton(
            modifier = Modifier.size(width = 180.dp, height = 40.dp),
            value = "Xác nhận",
            onClick = {
                authViewModel.resetPassword(
                    ResetPassRequest(
                        uiState.keyResetPass,
                        uiState.password
                    )
                )
            },
            icon = false,
            isBold = true,
            contentIcon = "change password",
        )
    }

}
