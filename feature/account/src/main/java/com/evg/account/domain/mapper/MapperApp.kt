package com.evg.account.domain.mapper

import com.evg.account.domain.model.AppStyle
import com.evg.account.domain.model.AppTheme

fun AppTheme.toIsDarkTheme(isDarkTheme: Boolean): Boolean {
    return when (this) {
        AppTheme.USER -> isDarkTheme
        AppTheme.LIGHT -> false
        AppTheme.DARK -> true
    }
}

fun AppStyle.toResourceAppStyle(): com.evg.ui.theme.AppStyle {
    return when (this) {
        AppStyle.PURPLE -> com.evg.ui.theme.AppStyle.PURPLE
        AppStyle.GREEN -> com.evg.ui.theme.AppStyle.GREEN
        AppStyle.BLUE -> com.evg.ui.theme.AppStyle.BLUE
    }
}