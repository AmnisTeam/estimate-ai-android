package com.evg.ui.theme.palettes

import androidx.compose.ui.graphics.Color
import com.evg.ui.theme.AppPalette

val purpleDarkPalette = AppPalette(
    primary = Color(0xFFC6B8FF),
    secondary = Color(0xFF4F378B),
    background = Color(0xFF161622),
    shimmer = Color.LightGray,

    text = Color(0xFFFFFFFF),

    // TextField
    textFieldPlaceholder = Color(0xFF6C6D8D),
    textFieldTitle = Color(0xFFAAAAAA),
    tileBackground = Color(0xFF1C1C2D),

    // BottomBar
    bottomBarSelected = Color(0xFF342F52),
)

val purpleLightPalette = AppPalette(
    primary = Color(0xFFC6B8FF),
    secondary = Color(0xFFC6B8FF),
    background = Color(0xFFFFFFFF),
    shimmer = Color.Gray,

    text = Color(0xFF000000),

    // TextField
    textFieldPlaceholder = Color(0xFF6C6D8D),
    textFieldTitle = Color(0xFFAAAAAA),
    tileBackground = Color(0xFFE7E3F6),

    // BottomBar
    bottomBarSelected = Color(0xFFB8AFE5),
)