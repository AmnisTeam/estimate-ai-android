package com.evg.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun EstimateAITheme(
    style: AppStyle = AppStyle.Purple,
    textSize: AppSize = AppSize.Medium,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (darkTheme) {
        true -> {
            when (style) {
                AppStyle.Purple -> purpleDarkPalette
            }
        }
        false -> {
            when (style) {
                AppStyle.Purple -> purpleDarkPalette
            }
        }
    }

    val typography = when(textSize) {
        AppSize.Medium -> mediumTextSize
    }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        content = content,
    )
}