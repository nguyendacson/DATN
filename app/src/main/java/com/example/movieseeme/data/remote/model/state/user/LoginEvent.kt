package com.example.movieseeme.data.remote.model.state.user

sealed class LoginEvent {
    data class OpenGoogleLogin(val url: String) : LoginEvent()
}