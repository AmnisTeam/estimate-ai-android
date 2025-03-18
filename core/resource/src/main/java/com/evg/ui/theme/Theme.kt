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
    style: AppStyle = AppStyle.Purple,
    textSize: AppSize = AppSize.Medium,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (darkTheme) {
        true -> {
            when (style) {
                AppStyle.Purple -> purpleDarkPalette
                AppStyle.Green -> greenDarkPalette
                AppStyle.Blue -> blueDarkPalette
            }
        }
        false -> {
            when (style) {
                AppStyle.Purple -> purpleLightPalette
                AppStyle.Green -> greenLightPalette
                AppStyle.Blue -> blueLightPalette
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