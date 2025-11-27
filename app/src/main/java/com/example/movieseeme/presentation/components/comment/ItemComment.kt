package com.example.movieseeme.presentation.components.comment

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.data.remote.model.request.comment.CommentCreateRequest
import com.example.movieseeme.data.remote.model.request.comment.CommentPatchRequest
import com.example.movieseeme.domain.model.movie.Comment
import com.example.movieseeme.domain.model.user.InformationUser
import com.example.movieseeme.presentation.theme.extension.orange
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@Composable
fun ItemComment(
    modifier: Modifier,
    userOther: Comment,
    onDeleteComment: (String) -> Unit,
    submitEdit: (CommentPatchRequest) -> Unit
) {
    Box(modifier = Modifier) {
        Row(modifier = Modifier) {
            AvatarOnComment(
                modifier = Modifier.size(31.dp),
                avatar = userOther.avatar
            )
            Spacer(modifier = Modifier.width(15.dp))
            TextOnComment(
                modifier = Modifier.weight(1f),
                userOther = userOther,
                onDeleteComment = { onDeleteComment(it) },
                onSubmit = {
                    submitEdit(it)
                    Log.d("ItemComment", "ItemComment: $it")
                },
            )
        }
    }
}

@Composable
fun ItemNewComment(
    modifier: Modifier,
    user: InformationUser,
    movieId: String,
    onSubmit: (CommentCreateRequest) -> Unit
) {
    Box(modifier = Modifier) {
        Row(modifier = Modifier) {
            AvatarOnComment(
                modifier = Modifier.size(31.dp),
                avatar = user.avatar
            )
            Spacer(modifier = Modifier.width(15.dp))
            TextOnNewComment(
                modifier = Modifier.weight(1f),
                user = user,
                getNewComment = { newComment ->
                    onSubmit(
                        CommentCreateRequest(
                            movieId = movieId,
                            content = newComment
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun ItemArrangement(
    modifier: Modifier,
    totalComment: String,
    onSelect: (SortType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = totalComment, style = MaterialTheme.typography.titleHeader2.copy(
                fontWeight = FontWeight.Normal
            )
        )
        SortButton(
            modifier = Modifier,
            onSelect = {onSelect(it)}
        )
    }
}

@Composable
fun AvatarOnComment(
    avatar: String,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .size(33.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green),
            painter = rememberAsyncImagePainter(model = avatar),
            contentScale = ContentScale.Crop,
            contentDescription = "avatar"
        )
    }
}

@Composable
fun TextOnComment(
    modifier: Modifier,
    userOther: Comment,
    onDeleteComment: (String) -> Unit,
    onSubmit: (CommentPatchRequest) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var isShowEdit by remember { mutableStateOf(false) }
    var newComment by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = userOther.name,
                style = MaterialTheme.typography.titleHeader2.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.orange
                )
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (isShowEdit) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                        .padding(horizontal = 3.dp, vertical = 2.dp),
                    value = newComment,
                    onValueChange = { newComment = it.trimStart() },
                    maxLines = 5,
                    textStyle = MaterialTheme.typography.titleHeader2.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            onSubmit(
                                CommentPatchRequest(
                                    commentId = userOther.id,
                                    newContent = newComment
                                )
                            )

                            isShowEdit = false
                        }
                    ),
                    decorationBox = { innerTextField ->
                        if (newComment.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = userOther.content,
                                    style = MaterialTheme.typography.titleHeader2.copy(
                                        fontWeight = FontWeight.Normal,
                                    )
                                )
                            }
                        }
                        innerTextField()
                    }

                )
            } else {
                Text(
                    text = userOther.content,
                    style = MaterialTheme.typography.titleHeader2.copy(
                        fontWeight = FontWeight.Normal,
                    )
                )
            }

        }
        Box() {
            IconButton(
                onClick = { if (userOther.owner) expanded = !expanded },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    Icons.Default.MoreVert,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Option comemnt"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Sửa") },
                    onClick = {
                        expanded = false
                        isShowEdit = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Xóa") },
                    onClick = {
                        expanded = false
                        onDeleteComment(userOther.id)
                    }
                )
            }
        }
    }
}

@Composable
fun TextOnNewComment(
    modifier: Modifier,
    user: InformationUser,
    getNewComment: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var newComment by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = user.name!!,
            style = MaterialTheme.typography.titleHeader2.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.orange
            )
        )
        Spacer(modifier = Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = Dp.Unspecified,
                    minHeight = 50.dp
                )
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth())
            {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = newComment,
                    onValueChange = { newComment = it.trimStart() },
                    maxLines = 5,
                    textStyle = MaterialTheme.typography.titleHeader2.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.background
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.background),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            getNewComment(newComment)
                            newComment = ""
                        }
                    ),
                    decorationBox = { innerTextField ->
                        if (newComment.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = "Bình luận của bạn...",
                                    style = MaterialTheme.typography.titleHeader2.copy(
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                                    )
                                )
                            }
                        }
                        innerTextField()
                    }

                )
                Spacer(modifier = Modifier.height(2.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    }
}


@Composable
fun SortButton(
    modifier: Modifier,
    onSelect: (SortType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                .clickable { expanded = !expanded }
                .padding(horizontal = 5.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Sắp xếp",
                style = MaterialTheme.typography.titleHeader2.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.background
                )
            )
            Spacer(Modifier.width(5.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                tint = MaterialTheme.colorScheme.background,
                contentDescription = "arraylist"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Cũ nhất") },
                onClick = {
                    expanded = false
                    onSelect(SortType.ASC)
                }
            )
            DropdownMenuItem(
                text = { Text("Mới nhất") },
                onClick = {
                    expanded = false
                    onSelect(SortType.DESC)
                }
            )
        }
    }
}

enum class SortType { ASC, DESC }

