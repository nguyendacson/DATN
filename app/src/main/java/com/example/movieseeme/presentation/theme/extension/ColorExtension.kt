package com.example.movieseeme.presentation.theme.extension

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val Shadow = Color(0xFF92FFF2)
val LineNav = Color(0xFF0296E5)
val Orange = Color(0xFFE54D2A)

val ColorScheme.shadow: Color
    get() = Shadow
val ColorScheme.lineNav: Color
    get() = LineNav

val ColorScheme.orange: Color
    get() = Orange