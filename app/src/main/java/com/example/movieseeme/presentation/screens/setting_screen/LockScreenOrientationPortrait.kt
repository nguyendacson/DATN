package com.example.movieseeme.presentation.screens.setting_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun LockScreenOrientationPortrait() {
    val context = LocalContext.current
    val activity = context as? Activity
    DisposableEffect(Unit) {
        // 🔒 Khóa hướng dọc
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {}
    }
}
@Composable
fun UnlockScreenOrientationSensor() {
    val context = LocalContext.current
    val activity = context as? Activity
    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        onDispose {}
    }
}
