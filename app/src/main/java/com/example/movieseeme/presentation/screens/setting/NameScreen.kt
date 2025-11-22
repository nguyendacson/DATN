package com.example.movieseeme.presentation.screens.setting

import CustomToast
import ItemTextField
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.user.UserViewModel

@Composable
fun NameScreen(
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
                title = "Tên",
                icon = Icons.Default.ArrowBackIosNew
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(modifier = Modifier.width(80.dp), contentAlignment = Alignment.CenterEnd) {
                    Text(
                        text = "Tên của bạn:",
                        style = MaterialTheme.typography.titleHeader2
                    )
                }

                Spacer(modifier = Modifier.size(5.dp))

                Text(
                    text = user?.name?.ifEmpty { "" } ?: "",
                    style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal)
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            ItemTextField(
                title = "Tên mới:",
                value = textState.name,
                onChanged = { userViewModel.onUserNameChange(it) }
            )

            Spacer(modifier = Modifier.height(30.dp))

            ButtonOnBox(
                modifier = Modifier.size(180.dp, 40.dp),
                value = "Cập nhật",
                isDark = false,
                onClick = {
                    focusManager.clearFocus()
                    userViewModel.updateNameAndEmailUser(textState.name)
                }
            )
        }
    }
    if (showToast && toastMessage.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = 60.dp),
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