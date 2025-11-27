package com.example.movieseeme.presentation.viewmodels.movie.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.local.SessionManager
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.remote.model.request.ChangePasswordRequest
import com.example.movieseeme.data.remote.model.request.UserUpdateRequest
import com.example.movieseeme.data.remote.model.state.user.UserSate
import com.example.movieseeme.data.repository.UserRepositoryImpl
import com.example.movieseeme.domain.model.user.InformationUser
import com.example.movieseeme.domain.model.user.Signature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _userState = MutableStateFlow(AppState())
    val userState: StateFlow<AppState> = _userState.asStateFlow()

    private val _uiTextState = MutableStateFlow(UserSate())
    val uiTextState: StateFlow<UserSate> = _uiTextState.asStateFlow()

    private val _user = MutableStateFlow<InformationUser?>(null)
    val user: StateFlow<InformationUser?> = _user.asStateFlow()

    private val _signature = MutableStateFlow<Signature?>(null)
    val signature: StateFlow<Signature?> = _signature.asStateFlow()

    fun clearMessage() {
        _userState.value = _userState.value.copy(message = "")
    }

    fun resetUserInput() {
        _uiTextState.value = UserSate()
    }

    fun onUserUserNameChange(username: String) {
        _uiTextState.value = _uiTextState.value.copy(username = username)
    }

    fun onUserNameChange(name: String) {
        _uiTextState.value = _uiTextState.value.copy(name = name)
    }

    fun onUserEmailChange(email: String) {
        _uiTextState.value = _uiTextState.value.copy(email = email)
    }

    fun onUserPasswordChange(password: String) {
        _uiTextState.value = _uiTextState.value.copy(password = password)
    }

    fun onUserNewPasswordChange(newPassword: String) {
        _uiTextState.value = _uiTextState.value.copy(newPassword = newPassword)
    }

    fun onUserConfirmPasswordChange(confirmPassword: String) {
        _uiTextState.value = _uiTextState.value.copy(confirmPassword = confirmPassword)
    }


    init {
        viewModelScope.launch {
            getMyInfo()
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            sessionManager.logout()
            sessionManager.resetLogout()
        }
    }


    suspend fun getMyInfo() {
        _userState.value = AppState(isLoading = true)
        when (val result = userRepositoryImpl.getMyInfo()) {
            is ApiResult.Success -> {
                _userState.value = AppState(
                    isLoading = false,
                    success = true
                )
                _user.value = result.data.result

            }

            is ApiResult.Error -> {
                _userState.value =
                    AppState(
                        isLoading = false,
                        success = false,
                    )
            }
        }

    }

    fun onChangeAvatar(file: File) {
        viewModelScope.launch {
            _userState.value = AppState(isLoading = true)

            val sigResult = userRepositoryImpl.getSignature()
            if (sigResult !is ApiResult.Success) return@launch
            val sig = sigResult.data

            val filePart = MultipartBody.Part.createFormData(
                "file", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
            )
            val apiKeyBody = sig.apiKey.toRequestBody("text/plain".toMediaTypeOrNull())
            val timestampBody =
                sig.timestamp.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val folderBody = sig.folder.toRequestBody("text/plain".toMediaTypeOrNull())
            val signatureBody = sig.signature.toRequestBody("text/plain".toMediaTypeOrNull())

            when (val uploadResult = userRepositoryImpl.uploadAvatar(
                cloudName = sig.cloudName,
                file = filePart,
                apiKey = apiKeyBody,
                timestamp = timestampBody,
                folder = folderBody,
                signature = signatureBody
            )) {
                is ApiResult.Success -> {
                    val secureUrl = uploadResult.data.secureUrl
                    val publicId = uploadResult.data.publicId

                    when (userRepositoryImpl.saveAvatar(
                        secure_url = secureUrl,
                        public_id = publicId
                    )) {

                        is ApiResult.Success -> {
                            getMyInfo()
                            _userState.value = AppState(
                                isLoading = false,
                                success = true,
                                message = "Thay đổi Ảnh đại diện thành công"
                            )

                        }

                        is ApiResult.Error -> {
                            _userState.value = AppState(
                                isLoading = false,
                                success = false,
                                message = "Thay đổi Ảnh đại diện không thành công"
                            )

                        }
                    }
                }

                is ApiResult.Error -> {
                    _userState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Thay đổi Ảnh đại diện không thành công"
                        )
                }
            }
        }
    }

    fun updateNameAndEmailUser(
        name: String? = null,
        email: String? = null,
        username: String? = null
    ) {
        _userState.value = AppState(isLoading = true)
        val finalName = name ?: _user.value?.name
        val finalUsername = username ?: _user.value?.username
        val finalEmail = email ?: _user.value?.email

        val request = UserUpdateRequest(
            username = finalUsername,
            name = finalName,
            email = finalEmail
        )

        viewModelScope.launch {
            when (val update = userRepositoryImpl.updateUser(request)) {
                is ApiResult.Success -> {
                    getMyInfo()
                    _userState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                            message = when {
                                email == null -> "Thay đổi Tên thành công"
                                else -> "Thay đổi Email thành công"
                            }
                        )
                }

                is ApiResult.Error -> {
                    _userState.value = AppState(
                        isLoading = false,
                        success = false,
                        message = when {
                            update.code == 1001 -> "Email đã tồn tại"
                            email == null -> "Thay đổi Tên không thành công"
                            else -> "Thay đổi Không thành công"
                        }
                    )
                }

            }
        }
    }

    fun changeUserPassword(password: String, newPassword: String) {
        _userState.value = AppState(isLoading = true)
        val request = ChangePasswordRequest(
            password = password,
            newPassword = newPassword,
        )

        viewModelScope.launch {
            when (userRepositoryImpl.changeUserPassword(request)) {
                is ApiResult.Success -> {
                    getMyInfo()
                    _userState.value =
                        AppState(
                            isLoading = false,
                            success = true,
                            message = "Thay đổi Mật khẩu thành công"
                        )
                }

                is ApiResult.Error -> {
                    _userState.value =
                        AppState(
                            isLoading = false,
                            success = false,
                            message = "Không thành công, hãy kiểm tra lại mật khẩu"
                        )
                }
            }
        }
    }


}