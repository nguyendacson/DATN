package com.example.movieseeme.presentation.theme.extension

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val Typography.titleHeader: TextStyle
    @Composable
    get() {
        val isDark = isSystemInDarkTheme()
        val gradientColors = if (isDark) {
            listOf(MaterialTheme.colorScheme.onBackground, Color.LightGray)
        } else {
            listOf(Color.LightGray, MaterialTheme.colorScheme.onBackground)
        }

        return TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            brush = Brush.verticalGradient(colors = gradientColors)
        )
    }

val Typography.titleHeader1: TextStyle
    @Composable
    get() {
        val isDark = isSystemInDarkTheme()
        val gradientColors = if (isDark) {
            MaterialTheme.colorScheme.background
        } else {
            MaterialTheme.colorScheme.background
        }

        return TextStyle(
            fontSize = 12.sp,
            color = gradientColors
        )
    }

val Typography.titleHeader2: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
    }



