package com.example.movieseeme.presentation.viewmodels.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.repository.UserRepositoryImpl
import com.example.movieseeme.domain.model.admin.CountMovie
import com.example.movieseeme.domain.model.admin.DetailUser
import com.example.movieseeme.domain.model.user.InformationUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {
    private val _adminState = MutableStateFlow(AppState())
    val userState: StateFlow<AppState> = _adminState.asStateFlow()

    private val _listUser = MutableStateFlow<List<InformationUser>>(emptyList())
    val listUser = _listUser.asStateFlow()

    private val _watchingCount = MutableStateFlow<List<CountMovie>>(emptyList())
    val watchingCount = _watchingCount.asStateFlow()

    private val _likeCount = MutableStateFlow<List<CountMovie>>(emptyList())
    val likeCount = _likeCount.asStateFlow()

    private val _infoUser = MutableStateFlow<DetailUser?>(null)
    val infoUser = _infoUser.asStateFlow()

    fun clearMessage() {
        _adminState.value = _adminState.value.copy(message = "", success = false)
    }


    fun getAllUser() {
        _adminState.value = AppState(isLoading = true)

        viewModelScope.launch {
            when (val result = userRepositoryImpl.getAllUser()) {
                is ApiResult.Success -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                        )
                    _listUser.value = result.data.result
                }

                is ApiResult.Error -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Không thành công"
                        )
                }
            }
        }
    }

    fun infoUser(key: String) {
        _adminState.value = AppState(isLoading = true)
        viewModelScope.launch {
            when (val result = userRepositoryImpl.getInfo(key = key)) {
                is ApiResult.Success -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                        )
                    _infoUser.value = result.data.result
                }

                is ApiResult.Error -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Không thành công"
                        )
                }
            }
        }
    }

    fun deleteUser(key: String) {
        _adminState.value = AppState(isLoading = true)
        viewModelScope.launch {
            when (userRepositoryImpl.deleteUser(key = key)) {
                is ApiResult.Success -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                            message = "Xóa thành công"
                        )
                }

                is ApiResult.Error -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Không thành công"
                        )
                }
            }
        }
    }

    fun deleteMovie(keySearch: String) {
        _adminState.value = AppState(isLoading = true)
        viewModelScope.launch {
            when (userRepositoryImpl.deleteMovie(keySearch = keySearch)) {
                is ApiResult.Success -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                            message = "Xóa thành công"
                        )
                }

                is ApiResult.Error -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Không thành công"
                        )
                }
            }
        }
    }

    fun watchingCount() {
        _adminState.value = AppState(isLoading = true)
        viewModelScope.launch {
            when (val result = userRepositoryImpl.watchingCount()) {
                is ApiResult.Success -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                        )
                    _watchingCount.value = result.data.result
                }

                is ApiResult.Error -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Không thành công"
                        )
                }
            }
        }
    }

    fun likeCount() {
        _adminState.value = AppState(isLoading = true)
        viewModelScope.launch {
            when (val result = userRepositoryImpl.likeCount()) {
                is ApiResult.Success -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                        )
                    _likeCount.value = result.data.result
                }

                is ApiResult.Error -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Không thành công"
                        )
                }
            }
        }
    }

    fun createDataMovie() {
        _adminState.value = _adminState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val result = userRepositoryImpl.createDataMovie() // suspend, chờ API
                when (result) {
                    is ApiResult.Success -> {
                        _adminState.value = _adminState.value.copy(
                            isLoading = false,
                            success = true,
                            message = "Tạo thành công"
                        )
                    }

                    is ApiResult.Error -> {
                        _adminState.value = _adminState.value.copy(
                            isLoading = false,
                            success = false,
                            message = "Không thành công"
                        )
                    }
                }
            } catch (e: Exception) {
                _adminState.value = _adminState.value.copy(
                    isLoading = false,
                    success = false,
                    message = e.message ?: "Lỗi không xác định"
                )
            }
        }
    }


    fun updateDataMovie() {
        _adminState.value = AppState(isLoading = true)
        viewModelScope.launch {
            when (userRepositoryImpl.updateDataMovie()) {
                is ApiResult.Success -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                            message = "Update data phim thành công"
                        )
                }

                is ApiResult.Error -> {
                    _adminState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Không thành công"
                        )
                }
            }
        }
    }
}