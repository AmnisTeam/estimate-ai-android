package com.evg.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.evg.ui.theme.palettes.blueDarkPalette
import com.evg.ui.theme.palettes.blueLightPalette
import com.evg.ui.theme.palettes.greenDarkPalette
import com.evg.ui.theme.palettes.greenLightPalette
import com.evg.ui.theme.palettes.purpleDarkPalette
import com.evg.ui.theme.palettes.purpleLightPalette

@Composable
fun EstimateAITheme(
    style: AppStyle = AppStyle.PURPLE,
    textSize: AppSize = AppSize.Medium,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (AppTheme.themeIsDark) {
        true -> {
            when (AppTheme.style) {
                AppStyle.PURPLE -> purpleDarkPalette
                AppStyle.GREEN -> greenDarkPalette
                AppStyle.BLUE -> blueDarkPalette
            }
        }
        false -> {
            when (AppTheme.style) {
                AppStyle.PURPLE -> purpleLightPalette
                AppStyle.GREEN -> greenLightPalette
                AppStyle.BLUE -> blueLightPalette
            }
        }
    }

    val typography = when(AppTheme.textSize) {
        AppSize.Medium -> mediumTextSize
    }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        content = content,
    )
}