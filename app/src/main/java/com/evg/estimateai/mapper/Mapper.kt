package com.evg.estimateai.mapper

import androidx.appcompat.app.AppCompatDelegate
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

fun SharedPrefsAppTheme.toIsDarkMode(): Int {
    return when (this) {
        SharedPrefsAppTheme.USER -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        SharedPrefsAppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        SharedPrefsAppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
    }
}
