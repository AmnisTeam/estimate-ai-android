package com.evg.ui.extensions

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

@Composable
fun Color.themedTint(factor: Float): Color {
    return if (isSystemInDarkTheme()) {
        lerp(this, Color.White, factor.coerceIn(0f, 1f))
    } else {
        lerp(this, Color.Black, factor.coerceIn(0f, 1f))
    }
}