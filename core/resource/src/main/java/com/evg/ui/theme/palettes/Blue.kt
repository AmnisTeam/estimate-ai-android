package com.evg.ui.theme.palettes

import androidx.compose.ui.graphics.Color
import com.evg.ui.theme.AppPalette

val blueDarkPalette = AppPalette(
    primary = Color(0xFF91A8D0),
    secondary = Color(0xFF375E97),
    background = Color(0xFF121C2B),
    shimmer = Color.LightGray,

    text = Color(0xFFFFFFFF),

    // TextField
    textField = Color(0xFF4A6FA5),
    textFieldName = Color(0xFFAAAAAA),
    textFieldBackground = Color(0xFF1A2940),

    // BottomBar
    bottomBarSelected = Color(0xFF2B4D6B),
)

val blueLightPalette = AppPalette(
    primary = Color(0xFF91A8D0),
    secondary = Color(0xFF375E97),
    background = Color(0xFFFFFFFF),
    shimmer = Color.Gray,

    text = Color(0xFF000000),

    // TextField
    textField = Color(0xFF4A6FA5),
    textFieldName = Color(0xFF666666),
    textFieldBackground = Color(0xFFDFE8F5),

    // BottomBar
    bottomBarSelected = Color(0xFFB0C8E5),
)
