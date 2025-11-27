package com.example.movieseeme.presentation.screens.help

import CustomToast
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.presentation.components.movies.item.RowHeader
import com.example.movieseeme.presentation.viewmodels.movie.FileUtils
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import java.io.File

data class ChatMessage(
    val message: String? = null,
    val imageUri: Uri? = null,
    val isUser: Boolean
)


@Composable
fun ChatScreen(
    navController: NavController,
    interactionViewModel: InteractionViewModel
) {
    val request by interactionViewModel.listRequest.collectAsState()
    val response by interactionViewModel.listResponse.collectAsState()
    val uiState by interactionViewModel.uiStateAction.collectAsState()

    var text by remember { mutableStateOf("") }

    val messages = remember(request, response) {
        buildList {
            request.forEachIndexed { index, req ->
                add(ChatMessage(req.message, req.imageUri, isUser = true)) // User
                if (index < response.size) {
                    add(ChatMessage(message = response[index], isUser = false)) // Bot
                }
            }
        }
    }

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFile by remember { mutableStateOf<File?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            selectedFile = FileUtils.uriToFile(context, it)
        }
    }

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }


    LaunchedEffect(uiState.message) {
        val msg = uiState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

//    LaunchedEffect(Unit) {
//        launcher.launch("image/*")
//    }

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
                title = "Trợ giúp",
                icon = Icons.Default.ArrowBackIosNew
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .weight(1f),
                reverseLayout = true
            ) {
                items(messages.reversed()) { msg ->
                    ChatMessageRow(msg)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(25.dp))
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add file",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 30.dp) // chiều cao tối thiểu
                            .background(Color.LightGray, shape = RoundedCornerShape(20.dp))
                            .padding(vertical = 4.dp)
                            .padding(start = 10.dp), // padding hợp lý
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (selectedFile != null) {
                            selectedImageUri?.let { uri ->
                                Image(
                                    painter = rememberAsyncImagePainter(uri),
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                        BasicTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .weight(1f)
//                            .heightIn(min = 40.dp)
                            ,
                            maxLines = 5,
                            decorationBox = { innerTextField ->
                                if (text.isEmpty()) {
                                    Text(
                                        text = "Nhập ...",
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        )

                        IconButton(
                            onClick = {
                                when {
                                    text.isNotBlank() -> {
                                        val chatMessage = ChatMessage(message = text, imageUri = null, isUser = true)
                                        interactionViewModel.addRequest(chatMessage)
                                        interactionViewModel.chat(message = text, file = null)
                                        text = ""
                                    }
                                    selectedFile != null -> {
                                        selectedImageUri?.let { uri ->
                                            val chatMessage = ChatMessage(
                                                message = null,
                                                imageUri = uri,
                                                isUser = true
                                            )
                                            interactionViewModel.addRequest(chatMessage)
                                            interactionViewModel.chat(null, FileUtils.uriToFile(context, uri))
                                        }
//                                        val file = selectedFile!!
//                                        val chatMessage = ChatMessage(message = null, imageUri = file, isUser = true)
//                                        interactionViewModel.addRequest(chatMessage) // thêm vào listRequest
//                                        interactionViewModel.chat(message = null, file = file)
                                        selectedFile = null
                                        selectedImageUri = null
                                        text = ""
                                    }
                                }
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = Color(0xFF0F9D58)
                            )
                        }
                    }

                }
            }
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

@Composable
fun ChatMessageRow(
    message: ChatMessage
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isUser) Color(0xFF0F9D58) else MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .widthIn(max = 250.dp)
        ) {
            when{
                message.imageUri != null -> {
                    Image(
                        painter = rememberAsyncImagePainter(message.imageUri),
                        contentDescription = null,
                        modifier = Modifier.size(90.dp)
                    )
                }
                message.message != null -> {
                    Text(
                        text = message.message,
                        color = if (message.isUser) Color.White else MaterialTheme.colorScheme.background,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
