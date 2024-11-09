package com.evg.ui.theme

import androidx.compose.ui.graphics.Color

data class AppPalette(
    val mainColor: Color,
    val singleTheme: Color,
    val oppositeTheme: Color,
    val buttonColor: Color,
)

enum class AppStyle {
    Purple,
}


val baseLightPalette = AppPalette(
    mainColor = Color.White,
    singleTheme = Color.White,
    oppositeTheme = Color.Black,
    buttonColor = Color(0xFFEFEEEE)
)

val baseDarkPalette = AppPalette(
    mainColor = Color.Black,
    singleTheme = Color.Black,
    oppositeTheme = Color.White,
    buttonColor = Color(0xFF2D2D31)
)

val purpleDarkPalette = baseDarkPalette.copy(
    mainColor = Color(0xFF9749E6)
)












/*
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)*/
