package com.example.movieseeme.presentation.screens.profile

import CustomToast
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.presentation.components.CustomButton
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.viewmodels.movie.FileUtils
import com.example.movieseeme.presentation.viewmodels.user.UserViewModel
import java.io.File

@Composable
fun AvatarPicker(
    userViewModel: UserViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFile by remember { mutableStateOf<File?>(null) }

    val userState by userViewModel.userState.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }


    LaunchedEffect(userState.message) {
        val msg = userState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    // Launcher mở gallery
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            selectedFile = FileUtils.uriToFile(context, it)
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch("image/*")
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 10.dp)
            .padding(bottom = 15.dp, top = 10.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                contentDescription = "back screen",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            selectedImageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(
                modifier = Modifier.size(180.dp, 40.dp),
                value = "Lưu",
                onClick = {
                    selectedFile?.let { file ->
                        userViewModel.onChangeAvatar(file)
                    }
                }
            )

            Spacer(modifier = Modifier.height(15.dp))
            CustomButton(
                modifier = Modifier.size(180.dp, 40.dp),
                value = "Chọn ảnh",
                onClick = {
                    launcher.launch("image/*")
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
