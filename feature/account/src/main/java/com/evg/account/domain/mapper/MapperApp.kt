package com.evg.account.domain.mapper

import androidx.appcompat.app.AppCompatDelegate
import com.evg.account.domain.model.AppStyle
import com.evg.account.domain.model.AppTheme

fun AppTheme.toAppCompatDelegateTheme(): Int {
    return when (this) {
        AppTheme.USER -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
    }
}

fun AppStyle.toResourceAppStyle(): com.evg.ui.theme.AppStyle {
    return when (this) {
        AppStyle.PURPLE -> com.evg.ui.theme.AppStyle.PURPLE
        AppStyle.GREEN -> com.evg.ui.theme.AppStyle.GREEN
        AppStyle.BLUE -> com.evg.ui.theme.AppStyle.BLUE
    }
}