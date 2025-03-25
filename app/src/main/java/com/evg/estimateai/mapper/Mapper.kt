package com.evg.estimateai.mapper

import com.evg.shared_prefs.domain.model.SharedPrefsAppStyle
import com.evg.shared_prefs.domain.model.SharedPrefsAppTheme
import com.evg.ui.theme.AppStyle

fun SharedPrefsAppStyle.toAppStyle(): AppStyle {
    return when (this) {
        SharedPrefsAppStyle.PURPLE -> AppStyle.PURPLE
        SharedPrefsAppStyle.GREEN -> AppStyle.GREEN
        SharedPrefsAppStyle.BLUE -> AppStyle.BLUE
    }
}

fun SharedPrefsAppTheme.toIsDarkMode(isDarkMode: Boolean): Boolean {
    return when (this) {
        SharedPrefsAppTheme.USER -> isDarkMode
        SharedPrefsAppTheme.LIGHT -> false
        SharedPrefsAppTheme.DARK -> true
    }
}
