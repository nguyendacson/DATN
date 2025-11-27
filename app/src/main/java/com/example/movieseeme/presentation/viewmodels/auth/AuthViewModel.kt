package com.example.movieseeme.presentation.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.remote.model.request.auth.LoginRequest
import com.example.movieseeme.data.remote.model.request.auth.SignUpRequest
import com.example.movieseeme.data.remote.model.request.auth.forgot_password.ResetPassRequest
import com.example.movieseeme.data.remote.model.state.user.LoginEvent
import com.example.movieseeme.data.remote.model.state.user.UserSate
import com.example.movieseeme.domain.auth_useCase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserSate())
    val uiState: StateFlow<UserSate> = _uiState.asStateFlow()

    private val _authState = MutableStateFlow(AppState())
    val authState: StateFlow<AppState> = _authState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>()
    val events = _events.asSharedFlow()

    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    fun onUsernameChange(value: String) {
        _uiState.value = _uiState.value.copy(username = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun confirmPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = value)
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value)
    }

    fun onKeyResetPassChange(value: String) {
        _uiState.value = _uiState.value.copy(keyResetPass = value)
    }

    fun setShowForgotPassword(show: Boolean) {
        _uiState.value = _uiState.value.copy(forGotPassword = show)
    }

    fun setSendEmail(show: Boolean) {
        _uiState.value = _uiState.value.copy(sendEmail = show)
    }

    fun resetLoginState() {
        _uiState.value = UserSate()
    }

    fun clearMessage() {
        _authState.value = AppState(message = "", isLoading = false, success = false)
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _authState.value = AppState(isLoading = true)
            when (val token = authUseCase.login(request)
            ) {
                is ApiResult.Success -> {
                    saveTokens(
                        token.data.result.accessToken,
                        token.data.result.refreshToken
                    )

                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        success = true,
                        message = "Đăng nhập thành công",
                    )
                }

                is ApiResult.Error -> {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        success = false,
                        message = "Kiểm tra lại tài khoản, mật khẩu"
                    )
                }
            }

        }
    }


    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            _authState.value = AppState(isLoading = true)

            when (val result = authUseCase.signUp(request)) {
                is ApiResult.Success -> {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        success = true,
                        message = "Đăng ký thành công"
                    )
                }

                is ApiResult.Error -> {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        success = false,
                        message = when (result.code) {
                            1001 -> "Email đã được sử dụng"
                            1002 -> "Email không ồn tại"
                            1000 -> "Tài khoản đã tồn tại"
                            else -> "Đăng kí không thành công"
                        }
                    )
                }
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true, message = null)

            when (val result = authUseCase.forgotPassword(email)) {
                is ApiResult.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = "Hãy kiểm tra Email của bạn",
                        sendEmail = true
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = "Không tìm thấy tài khoản"
                    )
                }
            }
        }
    }

    fun resetPassword(request: ResetPassRequest) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true, message = null)
            _authState.value = AppState(isLoading = true)
            when (val result = authUseCase.resetPassword(request)) {
                is ApiResult.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = result.data.success,
                        sendEmail = false,
                        forGotPassword = false
                    )

                    _authState.value = AppState(
                        isLoading = false,
                        message = "Đổi mật khẩu thành công"
                    )

                }

                is ApiResult.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = "Đổi mật khẩu không thành công"
                    )
                }
            }
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            authUseCase.cleanTokenUser()
        }
    }

    fun onGoogleLoginClicked() {
        viewModelScope.launch {
            _events.emit(LoginEvent.OpenGoogleLogin("https://sonnd03.io.vn/apiUser/auth/login/google"))
        }
    }

    fun saveTokens(accessToken: String?, refreshToken: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            if (accessToken != null && refreshToken != null) {
                _uiState.value = UserSate(
                    isLoading = false,
                    success = true,
                    message = "Đăng nhập thành công"
                )
                authUseCase.saveToken(accessToken, refreshToken)
            } else {
                _uiState.value = UserSate(
                    isLoading = false,
                    success = false,
                    message = "Đăng nhập không thành công"
                )
            }
        }
    }
}