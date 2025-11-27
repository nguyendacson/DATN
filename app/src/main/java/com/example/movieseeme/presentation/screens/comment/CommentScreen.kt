package com.example.movieseeme.presentation.screens.comment

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.movieseeme.presentation.components.comment.ItemArrangement
import com.example.movieseeme.presentation.components.comment.ItemComment
import com.example.movieseeme.presentation.components.comment.ItemNewComment
import com.example.movieseeme.presentation.components.comment.SortType
import com.example.movieseeme.presentation.viewmodels.movie.CommentViewModel
import com.example.movieseeme.presentation.viewmodels.movie.profile.UserViewModel

@Composable
fun CommentScreen(
    modifier: Modifier,
    movieId: String,
    commentViewModel: CommentViewModel,
    userViewModel: UserViewModel
) {
    DisposableEffect(commentViewModel) {
        onDispose {
            commentViewModel.clearComment()
        }
    }
    val comment by commentViewModel.comment.collectAsState()
    val user by userViewModel.user.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Color.Gray)
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        ItemArrangement(
            modifier = Modifier.fillMaxWidth(),
            totalComment = comment.size.toString() + " Comment",
            onSelect = {
                when (it) {
                    SortType.ASC -> commentViewModel.onStatusChange("asc")
                    SortType.DESC -> commentViewModel.onStatusChange("desc")
                }
                commentViewModel.getComments(movieId = movieId)
            })
        Spacer(modifier = Modifier.height(5.dp))
        ItemNewComment(
            modifier = Modifier.fillMaxWidth(),
            user = user!!,
            movieId = movieId,
            onSubmit = {
                if (it.content.isNotBlank()) {
                    commentViewModel.postComment(it)
                    commentViewModel.getComments(movieId = movieId)
                } else {
//
                }
            })
        if (comment.isNotEmpty()) {
            LazyColumn(modifier = Modifier.height(200.dp)) {
                items(comment) { item ->
                    Spacer(modifier = Modifier.height(5.dp))
                    ItemComment(
                        modifier = Modifier.fillMaxSize(),
                        userOther = item,
                        onDeleteComment = {
                            commentViewModel.deleteComment(it)
                            commentViewModel.getComments(movieId = movieId)
                        },
                        submitEdit = {
                            commentViewModel.editComment(it)
                            commentViewModel.getComments(movieId = movieId)
                        }
                    )
                }
            }

        }
    }
}