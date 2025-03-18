package com.evg.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.evg.ui.theme.palettes.blueDarkPalette
import com.evg.ui.theme.palettes.blueLightPalette
import com.evg.ui.theme.palettes.greenDarkPalette
import com.evg.ui.theme.palettes.greenLightPalette
import com.evg.ui.theme.palettes.purpleDarkPalette
import com.evg.ui.theme.palettes.purpleLightPalette

data class AppPalette(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val shimmer: Color,

    val text: Color,

    // TextField
    val textField: Color,
    val textFieldName: Color,
    val textFieldBackground: Color,

    // BottomBar
    val bottomBarSelected: Color,
)

enum class AppStyle {
    Purple, Green, Blue,
}

@Composable
fun getStyleColors(): List<Color> {
    val darkTheme = isSystemInDarkTheme()
    return if (darkTheme) {
        listOf(
            purpleDarkPalette.primary,
            greenDarkPalette.primary,
            blueDarkPalette.primary,
        )
    } else {
        listOf(
            purpleLightPalette.primary,
            greenLightPalette.primary,
            blueLightPalette.primary,
        )
    }
}
