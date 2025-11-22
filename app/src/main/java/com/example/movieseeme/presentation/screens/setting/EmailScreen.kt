package com.example.movieseeme.presentation.screens.setting

import CustomToast
import ItemTextField
import android.widget.Space
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.user.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.time.delay

@Composable
fun EmailScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val user by userViewModel.user.collectAsState()
    val textState by userViewModel.uiTextState.collectAsState()

    val userState by userViewModel.userState.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    var showVerifyDialog by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(userState.message) {
        val msg = userState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    LaunchedEffect(showVerifyDialog) {
        if (showVerifyDialog) {
            delay(5000)
            showVerifyDialog = false
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
                title = "Email",
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

                Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.CenterEnd) {
                    Text(
                        text = "Email của bạn:",
                        style = MaterialTheme.typography.titleHeader2
                    )
                }

                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = user?.email?.ifEmpty { "Chưa đăng kí Email" }
                        ?: "Chưa đăng kí Email",
                    style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 116.dp)
            ) {
                if (user?.emailVerified == false) {
                    Text(
                        text = "* Email chưa xác thực!",
                        style = MaterialTheme.typography.titleHeader2.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp
                        )
                    )
                } else {
                    Text(
                        text = "* Email đã xác thực!",
                        style = MaterialTheme.typography.titleHeader2.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color.Green,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            ItemTextField(
                title = "Email mới:",
                value = textState.email,
                onChanged = { userViewModel.onUserEmailChange(it) }
            )

            Spacer(modifier = Modifier.height(2.dp))

            if (textState.isEmailError || textState.email.isEmpty()) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 96.dp),
                    text = "* Email không hợp lệ",
                    style = MaterialTheme.typography.titleHeader2.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            ButtonOnBox(
                modifier = Modifier.size(180.dp, 40.dp),
                value = if (user?.email?.isNotBlank() == true) "Cập nhật" else "Đăng kí",
                isDark = false,
                onClick = {
                    keyboardController?.hide()
                    if (textState.isEmailError || textState.email.isBlank()) { }
                    else {
                        userViewModel.updateNameAndEmailUser(
                            name = user?.name,
                            email = textState.email
                        )
                    }

                }
            )
        }

        if (showVerifyDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable(enabled = true) { /* Chặn click xuống dưới */ },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.MarkEmailUnread,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "Vui lòng kiểm tra hộp thư!",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Chúng tôi đã gửi liên kết xác thực đến email mới của bạn. Hãy xác nhận để hoàn tất thay đổi.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { showVerifyDialog = false },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Đóng")
                        }
                    }
                }
            }
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