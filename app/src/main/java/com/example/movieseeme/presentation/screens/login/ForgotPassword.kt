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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.movieseeme.data.remote.model.auth.forgot_password.ResetPassRequest
import com.example.movieseeme.presentation.components.CustomButton
import com.example.movieseeme.presentation.components.DirectionalShadowTextField
import com.example.movieseeme.presentation.components.TextErrorInput
import com.example.movieseeme.presentation.theme.extension.titleHeader
import com.example.movieseeme.presentation.viewmodels.UserViewModel


@Composable
fun ForgotPassword(
    viewModel: UserViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
) {
    BackHandler(enabled = true) { viewModel.setShowForgotPassword(false) }
    val uiState by viewModel.uiState.collectAsState()
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
                    InputNewPassword(viewModel = viewModel)
                } else {
                    SendEmail(viewModel = viewModel)
                }
            }
        }

    }
}

@Composable
fun SendEmail(viewModel: UserViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Text(
        text = "Enter email to reset password",
        style = MaterialTheme.typography.titleHeader.copy(fontSize = 18.sp)
    )

    Spacer(modifier = Modifier.size(16.dp))

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        DirectionalShadowTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            icon = false,
            placeholder = "Email",
            isError = uiState.isEmailError
        )
        if (uiState.isEmailError) {
            Spacer(modifier = Modifier.height(5.dp))
            TextErrorInput(
                modifier = Modifier.align(Alignment.Start),
                title = "*Least 8 characters"
            )
        }
    }

    Spacer(modifier = Modifier.size(16.dp))

    CustomButton(
        modifier = Modifier.size(width = 180.dp, height = 40.dp),
        value = "Send Email",
        onClick = {
            viewModel.forgotPassword(uiState.email)
        },
        icon = false,
        isBold = true,
        contentIcon = "Send Email",
    )
}

@Composable
fun InputNewPassword(viewModel: UserViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler(enabled = true) { viewModel.setSendEmail(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { viewModel.setSendEmail(false) },
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
            text = "Enter email to reset password",
            style = MaterialTheme.typography.titleHeader.copy(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        DirectionalShadowTextField(
            value = uiState.keyResetPass,
            onValueChange = viewModel::onKeyResetPassChange,
            icon = false,
            placeholder = "Key",
            isError = false
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            DirectionalShadowTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                icon = true,
                placeholder = "New password",
                isError = uiState.isPasswordError
            )
            if (uiState.isPasswordError) {
                Spacer(modifier = Modifier.height(5.dp))
                TextErrorInput(
                    modifier = Modifier.align(Alignment.Start),
                    title = "*Least 8 characters"
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        CustomButton(
            modifier = Modifier.size(width = 180.dp, height = 40.dp),
            value = "Change password",
            onClick = {
                viewModel.resetPassword(
                    ResetPassRequest(uiState.username, uiState.password)
                )
            },
            icon = false,
            isBold = true,
            contentIcon = "change password",
        )
    }

}
