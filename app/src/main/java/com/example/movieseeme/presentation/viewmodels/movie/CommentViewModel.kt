package com.example.movieseeme.presentation.viewmodels.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.remote.model.request.comment.CommentCreateRequest
import com.example.movieseeme.data.remote.model.request.comment.CommentPatchRequest
import com.example.movieseeme.data.remote.model.state.movie.MovieFilter
import com.example.movieseeme.data.repository.MovieRepositoryImpl
import com.example.movieseeme.domain.model.movie.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl
) : ViewModel() {
    private val _commentState = MutableStateFlow(AppState())
    val commentState: StateFlow<AppState> = _commentState.asStateFlow()

    private val _filterComment = MutableStateFlow(MovieFilter())
    val filterComment: StateFlow<MovieFilter> = _filterComment.asStateFlow()

    fun onStatusChange(sort: String) {
        _filterComment.value = _filterComment.value.copy(sort = sort)
    }
    private val _comment = MutableStateFlow<List<Comment>>(emptyList())
    val comment: StateFlow<List<Comment>> = _comment.asStateFlow()

    fun clearComment() {
        _comment.value = emptyList()
    }

    fun clearMessage() {
        _commentState.value = AppState(success = false, message = "")
    }

    fun setMessage(message: String, success: Boolean) {
        _commentState.value = _commentState.value.copy(message = message, success = success)
    }

    fun getComments(
        movieId: String
    ) {
        viewModelScope.launch {
            _commentState.value = AppState(isLoading = true)
            val filter = filterComment.value

            when (val result = movieRepositoryImpl.getComments(
                movieId = movieId,
                sort = filter.sort!!
            )) {
                is ApiResult.Success -> {
                    _comment.value = result.data.result
                    Log.d("getComments", _comment.value.toString())
                }

                is ApiResult.Error -> {
                    //
                }
            }
        }
    }

    fun postComment(commentCreateRequest: CommentCreateRequest) {
        viewModelScope.launch {
            _commentState.value = AppState(isLoading = true)
            when (val result =
                movieRepositoryImpl.postComment(commentRequest = commentCreateRequest)) {
                is ApiResult.Success -> {
                    _commentState.value = AppState(
                        isLoading = false,
                        success = true,
                        message = "Đã thêm bình luận"
                    )
                    getComments(commentCreateRequest.movieId)
                }

                is ApiResult.Error -> {
                    _commentState.value = AppState(
                        isLoading = false,
                        success = true,
                        message = when (result.code) {
                            2005 -> "Đạt giới hạn bình luận phim này"
                            else -> "Bình luận không thành công"
                        }
                    )
                }
            }
        }
    }

    fun editComment(request: CommentPatchRequest) {
        viewModelScope.launch {
            _commentState.value = AppState(isLoading = true)
            when (val result = movieRepositoryImpl.updateComment(request)) {
                is ApiResult.Success -> {
                    _commentState.value = AppState(
                        isLoading = false,
                        success = true,
                        message = "Đã sửa bình luận"
                    )
                }

                is ApiResult.Error -> {
                    _commentState.value = AppState(
                        isLoading = false,
                        success = true,
                        message = "Sửa bình luận không thành công" + result.message
                    )
                }
            }
        }
    }

    fun deleteComment(commentId: String) {
        viewModelScope.launch {
            _commentState.value = AppState(isLoading = true)
            when (movieRepositoryImpl.deleteComment(commentId = commentId)) {
                is ApiResult.Success -> {
                    _commentState.value = AppState(
                        isLoading = false,
                        success = true,
                        message = "Xóa bình luận thành công"
                    )
                }

                is ApiResult.Error -> {
                    _commentState.value = AppState(
                        isLoading = false,
                        success = true,
                        message = "Xóa bình luận không thành công"
                    )
                }
            }
        }
    }
}