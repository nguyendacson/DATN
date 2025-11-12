package com.example.movieseeme.presentation.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.auth.LoginRequest
import com.example.movieseeme.data.remote.model.auth.SignUpRequest
import com.example.movieseeme.data.remote.model.auth.forgot_password.ResetPassRequest
import com.example.movieseeme.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class LoginEvent {
    data class OpenGoogleLogin(val url: String) : LoginEvent()
}

data class UserUiSate(
    var sendEmail: Boolean = false,
    var forGotPassword: Boolean = false,
    var click: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null,
    val success: Boolean? = false,
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val keyResetPass: String = "",
) {
    val isUserNameError get() = username.length < 8
    val isPasswordError get() = password.length < 8
    val isConfirmPasswordError get() = confirmPassword != password || confirmPassword.isEmpty()
    val isEmailError get() = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiSate())
    val uiState: StateFlow<UserUiSate> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>()
    val events = _events.asSharedFlow()

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
        _uiState.value = _uiState.value.copy(
            success = null,
            message = null,
            isLoading = false,
            password = " ",
            username = " "
        )
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _uiState.value = UserUiSate(isLoading = true)

            when (val result = authUseCase(request)) {
                is ApiResult.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = true,
                        message = result.data.message,
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = result.message
                    )
                }
            }

        }
    }

    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)

            when (val result = authUseCase.signUp(request)) {
                is ApiResult.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = result.data.success,
                        message = result.data.message
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = result.message
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
                        success = result.data.success,
                        message = "Please check your email",
                        sendEmail = true
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = result.message
                    )
                }
            }
        }
    }

    fun resetPassword(request: ResetPassRequest) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true, message = null)

            when (val result = authUseCase.resetPassword(request)) {
                is ApiResult.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = result.data.success,
                        message = result.data.result,
                        sendEmail = false
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = result.message
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
            Log.d("LOGIN", "onGoogleLoginClicked")
            _events.emit(LoginEvent.OpenGoogleLogin("https://sonnd03.io.vn/apiUser/auth/login/google"))
        }
    }

    fun saveTokens(accessToken: String?, refreshToken: String?) {
        viewModelScope.launch {
            if (accessToken != null && refreshToken != null) {
                _uiState.value = UserUiSate(
                    isLoading = false,
                    success = true,
                    message = "Login Successful"
                )
                authUseCase(accessToken, refreshToken)
            } else {
                _uiState.value = UserUiSate(
                    isLoading = false,
                    success = false,
                    message = "Login fail"
                )
            }
        }
    }
}




