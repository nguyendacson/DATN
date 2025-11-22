package com.example.movieseeme.presentation.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.movieseeme.presentation.theme.extension.shadow
import com.example.movieseeme.presentation.theme.extension.titleHeader1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShadowTextField(
    value: String,
    onValueChange: (String) -> Unit,
    icon: Boolean,
    placeholder: String,
    isError: Boolean = false
) {
    val shadowColor = MaterialTheme.colorScheme.shadow
    val cornerShape = RoundedCornerShape(6.dp)
    val errorColor = MaterialTheme.colorScheme.error
    val colorBackground = MaterialTheme.colorScheme.background
    val colorOnBackground = MaterialTheme.colorScheme.onBackground

    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .background(
                    color = if (isError) errorColor.copy(alpha = 0.8f) else shadowColor.copy(
                        alpha = 0.8f
                    ),
                    cornerShape.copy(bottomStart = CornerSize(12.dp), topEnd = CornerSize(12.dp))
                )
                .blur(radius = 11.dp)
        )
        var isPasswordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = value,
            textStyle = MaterialTheme.typography.titleHeader1,
            onValueChange = onValueChange,
            placeholder = {
                if (!isFocused && value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.titleHeader1.copy(
                            color = colorBackground.copy(
                                alpha = 0.6f
                            )
                        )
                    )
                }
            },
            shape = cornerShape,
            singleLine = true,
            visualTransformation = when {
                icon && !isPasswordVisible -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = if (isError) errorColor else colorBackground,
                unfocusedTextColor = if (isError) errorColor else colorBackground,
                focusedBorderColor = if (isError) errorColor else colorOnBackground,
                unfocusedBorderColor = if (isError) errorColor else colorOnBackground,
                focusedContainerColor = colorOnBackground,
                unfocusedContainerColor = colorOnBackground,
                cursorColor = Color.Green
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            trailingIcon = {
                if (icon) {
                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            tint = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                            contentDescription = "Hind password visibility"
                        )
                    }
                }
            },
        )
    }
}