package com.evg.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

object AppTheme {
    var style by mutableStateOf(AppStyle.PURPLE)
    var nightMode: Int by mutableIntStateOf(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    var textSize by mutableStateOf(AppSize.Medium)

    val colors: AppPalette
        @Composable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current
}


val LocalAppColors = staticCompositionLocalOf<AppPalette> {
    error("Colors composition error")
}

val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("Typography composition error")
}
