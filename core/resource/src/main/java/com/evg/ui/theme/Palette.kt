package com.evg.ui.theme

import androidx.compose.ui.graphics.Color

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
    PURPLE, GREEN, BLUE,
}
