package com.example.movieseeme.presentation.screens.login

import CustomToast
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.movieseeme.R
import com.example.movieseeme.data.remote.model.request.auth.LoginRequest
import com.example.movieseeme.data.remote.model.state.user.LoginEvent
import com.example.movieseeme.presentation.components.CustomButton
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.TextErrorInput
import com.example.movieseeme.presentation.components.user.ShadowTextField
import com.example.movieseeme.presentation.components.lock_screen.LockScreenOrientationPortrait
import com.example.movieseeme.presentation.theme.extension.titleHeader
import com.example.movieseeme.presentation.theme.extension.titleHeader1
import com.example.movieseeme.presentation.viewmodels.auth.AuthViewModel
import kotlin.math.roundToInt

@SuppressLint("ConfigurationScreenWidthHeight", "ContextCastToActivity")
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    LockScreenOrientationPortrait()


    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val density = LocalDensity.current
    val screenHeightPx = with(density) { screenHeightDp.toPx() }
    val onBackground = MaterialTheme.colorScheme.onBackground
    val styleTitle1 = MaterialTheme.typography.titleHeader1
    val infiniteTransition = rememberInfiniteTransition(label = "background_scroll")
    val translationY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -screenHeightPx,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scrollY"
    )
    val context = LocalContext.current
    val activity = context as Activity
    val uiState by authViewModel.uiState.collectAsState()
    val authState by authViewModel.authState.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(authState.message) {
        val msg = authState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }


    LaunchedEffect(authViewModel) {
        authViewModel.events.collect { event ->
            when (event) {
                is LoginEvent.OpenGoogleLogin -> {
                    val intent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                    activity.startActivity(intent)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val uri = activity.intent?.data
        if (uri != null) {
            val accessToken = uri.getQueryParameter("accessToken")
            val refreshToken = uri.getQueryParameter("refreshToken")
            authViewModel.saveTokens(accessToken = accessToken, refreshToken = refreshToken)

            activity.intent.data = null
        }
    }

    LaunchedEffect(authState.success) {
        if (authState.success == true) {
            navController.navigate("home") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            authViewModel.resetLoginState()
            authViewModel.clearMessage()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = "background 1",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, translationY.roundToInt()) }
        )

        Image(
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = "background 2",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, translationY.roundToInt() + screenHeightPx.toInt()) }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                        .copy(alpha = 0.8f)
                        .compositeOver(onBackground.copy(alpha = 0.2f))
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConstraintLayout {
                val (tvLogo, tvHeader, tvHeader1, clLogin) = createRefs()
                val isDark = isSystemInDarkTheme()
                if (isDark) {
                    Image(
                        painter = painterResource(id = R.drawable.item_logo_light),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(360.dp)
                            .constrainAs(tvLogo) {
                                top.linkTo(parent.top, margin = (-20).dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.item_logo_black),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(360.dp)
                            .constrainAs(tvLogo) {
                                top.linkTo(parent.top, margin = (-20).dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }

                Text(
                    modifier = Modifier.constrainAs(tvHeader) {
                        top.linkTo(tvLogo.bottom, margin = (-40).dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    text = "Xin Chào",
                    style = MaterialTheme.typography.titleHeader,
                )

                Text(
                    modifier = Modifier.constrainAs(tvHeader1) {
                        top.linkTo(tvHeader.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    text = "Nhập thông tin của bạn",
                    style = styleTitle1.copy(
                        color = onBackground
                    )
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .constrainAs(clLogin) {
                            top.linkTo(tvHeader1.bottom, margin = 35.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ShadowTextField(
                        value = uiState.username,
                        onValueChange = authViewModel::onUsernameChange,
                        placeholder = "Tài khoản",
                        isError = uiState.isUserNameError,
                        icon = false
                    )
                    if (uiState.isUserNameError) {
                        Spacer(modifier = Modifier.height(5.dp))
                        TextErrorInput(
                            modifier = Modifier.align(Alignment.Start),
                            title = "* Ít nhất 8 kí tự"
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    ShadowTextField(
                        value = uiState.password,
                        onValueChange = authViewModel::onPasswordChange,
                        placeholder = "Mật khẩu",
                        isError = uiState.isPasswordError,
                        icon = true
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (uiState.isPasswordError) {
                            TextErrorInput(
                                modifier = Modifier,
                                title = "* Ít nhất 8 kí tự"
                            )
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        Text(
                            text = "Quên mật khẩu?",
                            modifier = Modifier
                                .clickable {
                                    authViewModel.setShowForgotPassword(true)
                                },
                            style = styleTitle1.copy(color = onBackground)
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    CustomButton(
                        modifier = Modifier.size(width = 180.dp, height = 45.dp),
                        value = "Đăng nhập",
                        onClick = {
                            authViewModel.login(LoginRequest(uiState.username, uiState.password))

                        },
                        icon = false,
                        contentIcon = "Login with username",
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.width(110.dp),
                            thickness = 2.dp,
                            color = onBackground.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Khác",
                            style = styleTitle1.copy(
                                color = onBackground,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        HorizontalDivider(
                            modifier = Modifier.width(110.dp),
                            thickness = 2.dp,
                            color = onBackground.copy(alpha = 0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomButton(
                        modifier = Modifier.size(width = 220.dp, height = 45.dp),
                        value = "Đăng nhập với Google",
                        onClick = {
                            authViewModel.onGoogleLoginClicked()
                        },
                        icon = true,
                        isBold = false,
                        itemIcon = R.drawable.icon_google,
                        contentIcon = "Login with google",
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Bạn chưa có tài khoản?",
                            style = styleTitle1.copy(
                                color = onBackground
                            )
                        )
                        Text(
                            text = "Đăng kí",
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .clickable {
                                    navController.navigate("signUp")
                                },
                            style = styleTitle1.copy(
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                if (showToast && toastMessage.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 30.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        CustomToast(
                            value = toastMessage,
                            onDismiss = {
                                showToast = false
                                authViewModel.clearMessage()
                            }
                        )
                    }
                }
            }


        }
        if (authState.isLoading) {
            LoadingBounce()
        }
        if (uiState.forGotPassword) {
            ForgotPassword(
                authViewModel = authViewModel,
                onDismiss = { authViewModel.setShowForgotPassword(false) }
            )
        }
    }
}