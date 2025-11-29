package com.example.movieseeme.presentation.screens.home.admin

import CustomToast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.model.admin.CountMovie
import com.example.movieseeme.domain.model.admin.DetailUser
import com.example.movieseeme.domain.model.user.InformationUser
import com.example.movieseeme.presentation.components.CustomButton
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.theme.extension.orange
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.admin.AdminViewModel

@Composable
fun HomeAdminScreen(
    adminViewModel: AdminViewModel
) {
    val adminState by adminViewModel.userState.collectAsState()
    val listUser by adminViewModel.listUser.collectAsState()
    val watchingCount by adminViewModel.watchingCount.collectAsState()
    val likeCount by adminViewModel.likeCount.collectAsState()
    val infoUser by adminViewModel.infoUser.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(adminState.message) {
        adminState.message?.let { msg ->
            toastMessage = msg
            showToast = true
        }
    }


    val horizontalScroll = rememberScrollState()
    val horizontalScroll1 = rememberScrollState()
    val horizontalScrollWC = rememberScrollState()
    val horizontalScrollLike = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // all User
            item {
                Column(
                    modifier = Modifier
                        .heightIn(min = 10.dp, max = 500.dp)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomButton(
                        modifier = Modifier.size(250.dp, 50.dp),
                        value = "Xem tất cả người dùng",
                        onClick = {
                            adminViewModel.getAllUser()
                        },
                        itemIcon = 0
                    )

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(horizontalScroll)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth() // Giữ width gọn gàng

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                    .padding(8.dp)
                            ) {
                                TableCell("Name", 150.dp)
                                TableCell("Username", 120.dp)
                                TableCell("Email", 200.dp)
                                TableCell("Verified", 80.dp)
                                TableCell("Provider", 80.dp)
                            }

                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                            ) {
                                listUser.forEach { user ->
                                    TableRowUser(user)
                                }
                            }
                        }
                    }

                }
            }

            // watching
            item {
                Column(
                    modifier = Modifier
                        .heightIn(min = 10.dp, max = 500.dp)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomButton(
                        modifier = Modifier.size(250.dp, 50.dp),
                        value = "Thống kê lượt xem",
                        onClick = {
                            adminViewModel.watchingCount()
                        },
                        itemIcon = 0
                    )

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(horizontalScrollWC)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth() // Giữ width gọn gàng

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                    .padding(8.dp)
                            ) {
                                TableCell("MovieId", 150.dp)
                                TableCell("Name", 120.dp)
                                TableCell("Slug", 200.dp)
                                TableCell("Count", 80.dp)
                            }

                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                            ) {
                                watchingCount.forEach { watching ->
                                    TableRowCount(watching)
                                }
                            }
                        }
                    }

                }
            }

            // Like
            item {
                Column(
                    modifier = Modifier
                        .heightIn(min = 10.dp, max = 500.dp)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomButton(
                        modifier = Modifier.size(250.dp, 50.dp),
                        value = "Thống kê lượt like",
                        onClick = {
                            adminViewModel.likeCount()
                        },
                        itemIcon = 0
                    )

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(horizontalScrollLike)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth() // Giữ width gọn gàng

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                    .padding(8.dp)
                            ) {
                                TableCell("MovieId", 150.dp)
                                TableCell("Name", 120.dp)
                                TableCell("Slug", 200.dp)
                                TableCell("Count", 80.dp)
                            }

                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                            ) {
                                likeCount.forEach { like ->
                                    TableRowCount(like)
                                }
                            }
                        }
                    }

                }
            }

            // Infor User
            item {
                Column(
                    modifier = Modifier
                        .heightIn(max = 500.dp)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var value by remember { mutableStateOf("") }

                    CustomButton(
                        modifier = Modifier.size(250.dp, 50.dp),
                        value = "Thông tin chi tiết Người Xem",
                        onClick = {
                            if (value.isEmpty()) return@CustomButton
                            adminViewModel.infoUser(key = value)
                            value = ""
                        },
                        itemIcon = 0
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .padding(horizontal = 16.dp)
                            .border(
                                width = 1.5.dp,
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.orange
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            value = value,
                            onValueChange = { value = it },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                            decorationBox = { innerTextField ->
                                if (value.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Nhập email hoặc username",
                                            style = MaterialTheme.typography.titleHeader2.copy(
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colorScheme.onBackground.copy(
                                                    alpha = 0.5f
                                                )
                                            )
                                        )
                                    }
                                }
                                innerTextField()
                            }

                        )
                    }


                    Spacer(Modifier.height(10.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(horizontalScroll1)
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(100.dp)
                                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                    .padding(8.dp)
                            ) {
                                TableCell("Name", 100.dp)
                                TableCell("Username", 100.dp)
                                TableCell("Avatar", 100.dp)
                                TableCell("Email", 100.dp)
                                TableCell("Dob", 100.dp)
                                TableCell("Verified", 100.dp)
                                TableCell("Roles", 100.dp)
                                TableCell("Permissions", 100.dp)
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                infoUser?.let {
                                    TableColumDetailMovie(it)
                                }
                            }

                        }
                    }

                }
            }

            // Delete User
            item {
                Column(
                    modifier = Modifier
                        .heightIn(max = 500.dp)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var value by remember { mutableStateOf("") }

                    CustomButton(
                        modifier = Modifier.size(250.dp, 50.dp),
                        value = "Xóa người dùng",
                        onClick = {
                            if (value.isEmpty()) return@CustomButton
                            adminViewModel.deleteUser(key = value)
                            value = ""
                        },
                        itemIcon = 0
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .padding(horizontal = 16.dp)
                            .border(
                                width = 1.5.dp,
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.orange
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            value = value,
                            onValueChange = { value = it },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                            decorationBox = { innerTextField ->
                                if (value.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Nhập email hoặc username",
                                            style = MaterialTheme.typography.titleHeader2.copy(
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colorScheme.onBackground.copy(
                                                    alpha = 0.5f
                                                )
                                            )
                                        )
                                    }
                                }
                                innerTextField()
                            }

                        )
                    }
                }
            }

            // Delete Movie
            item {
                Column(
                    modifier = Modifier
                        .heightIn(max = 500.dp)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var value by remember { mutableStateOf("") }

                    CustomButton(
                        modifier = Modifier.size(250.dp, 50.dp),
                        value = "Xóa phim",
                        onClick = {
                            if (value.isEmpty()) return@CustomButton
                            adminViewModel.deleteMovie(keySearch = value)
                            value = ""
                        },
                        itemIcon = 0
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .padding(horizontal = 16.dp)
                            .border(
                                width = 1.5.dp,
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.orange
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            value = value,
                            onValueChange = { value = it },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                            decorationBox = { innerTextField ->
                                if (value.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Nhập slug (ten-phim-nam)",
                                            style = MaterialTheme.typography.titleHeader2.copy(
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colorScheme.onBackground.copy(
                                                    alpha = 0.5f
                                                )
                                            )
                                        )
                                    }
                                }
                                innerTextField()
                            }

                        )
                    }
                }
            }

            // Update Movie
            item {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    CustomButton(
                        modifier = Modifier.size(250.dp, 50.dp),
                        value = "Tự động thêm phim",
                        onClick = {
                            adminViewModel.createDataMovie()
                        },
                        itemIcon = 0
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    CustomButton(
                        modifier = Modifier.size(250.dp, 50.dp),
                        value = "Update tập phim",
                        onClick = {
                            adminViewModel.updateDataMovie()
                        },
                        itemIcon = 0
                    )
                }
            }
        }
        if (adminState.isLoading) {
            LoadingBounce()
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
                        adminViewModel.clearMessage()
                    }
                )
            }
        }
    }
}

@Composable
fun TableRowUser(user: InformationUser) {
    Row(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TableCell(user.name ?: "Không có", 150.dp)
        TableCell(user.username ?: "Không có", 120.dp)
        TableCell(user.email ?: "Không có", 200.dp)
        TableCell(user.emailVerified.toString(), 80.dp)
        TableCell(user.provider ?: "Không có", 80.dp)
    }
}

@Composable
fun TableColumDetailMovie(info: DetailUser) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TableCell1(info.name ?: "Không có")
        TableCell1(info.username ?: "Không có")
        TableCell1(info.avatar ?: "Không có")
        TableCell1(info.email ?: "Không có")
        TableCell1(info.dob.toString() ?: "Không có")
        TableCell1(info.emailVerified.toString() ?: "Không có")
        TableCell1(info.roles.map { it.name }.toString())
        TableCell1(info.roles.map { it.description }.toString())
    }
}

@Composable
fun TableRowCount(countMovie: CountMovie) {
    Row(
        modifier = Modifier
            .padding(5.dp)
    ) {
        TableCell(countMovie.movieId, 150.dp)
        TableCell(countMovie.name, 120.dp)
        TableCell(countMovie.slug, 200.dp)
        TableCell(countMovie.watchCount.toString(), 80.dp)
    }
}

@Composable
fun TableCell(
    text: String,
    width: Dp
) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        SelectionContainer {
            Text(
                text = text,
                style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun TableCell1(
    text: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        SelectionContainer {
            Text(
                text = text,
                style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}