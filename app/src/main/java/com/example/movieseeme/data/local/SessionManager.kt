package com.example.movieseeme.data.local

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SessionManager @Inject constructor() {
    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut = _isLoggedOut

    fun logout() {
        _isLoggedOut.value = true
    }

    fun resetLogout() {
        _isLoggedOut.value = false
    }
}
