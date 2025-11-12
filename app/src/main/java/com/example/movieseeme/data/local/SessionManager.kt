package com.example.movieseeme.data.local

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class SessionManager @Inject constructor() {
    private  val _isLoggedOut = MutableSharedFlow<Boolean>()
    val isLoggedOut: SharedFlow<Boolean> = _isLoggedOut

    suspend fun logout(){
        _isLoggedOut.emit(true)
    }
}