package com.evg.account.domain.mapper

import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.AppStyle
import com.evg.account.domain.model.AppTheme
import com.evg.account.domain.model.TestingLanguage
import com.evg.shared_prefs.domain.model.SharedPrefsAppLanguage
import com.evg.shared_prefs.domain.model.SharedPrefsAppStyle
import com.evg.shared_prefs.domain.model.SharedPrefsAppTheme
import com.evg.shared_prefs.domain.model.SharedPrefsTestingLanguage

fun AppLanguage.toSharedPrefsAppLanguage(): SharedPrefsAppLanguage {
    return when (this) {
        AppLanguage.USER -> SharedPrefsAppLanguage.USER
        AppLanguage.ENGLISH -> SharedPrefsAppLanguage.ENGLISH
        AppLanguage.RUSSIAN -> SharedPrefsAppLanguage.RUSSIAN
    }
}
fun SharedPrefsAppLanguage.toAccountAppLanguage(): AppLanguage {
    return when (this) {
        SharedPrefsAppLanguage.USER -> AppLanguage.USER
        SharedPrefsAppLanguage.ENGLISH -> AppLanguage.ENGLISH
        SharedPrefsAppLanguage.RUSSIAN -> AppLanguage.RUSSIAN
    }
}

fun AppTheme.toSharedPrefsAppTheme(): SharedPrefsAppTheme {
    return when (this) {
        AppTheme.USER -> SharedPrefsAppTheme.USER
        AppTheme.LIGHT -> SharedPrefsAppTheme.LIGHT
        AppTheme.DARK -> SharedPrefsAppTheme.DARK
    }
}
fun SharedPrefsAppTheme.toAppTheme(): AppTheme {
    return when (this) {
        SharedPrefsAppTheme.USER -> AppTheme.USER
        SharedPrefsAppTheme.LIGHT -> AppTheme.LIGHT
        SharedPrefsAppTheme.DARK -> AppTheme.DARK
    }
}

fun TestingLanguage.toSharedPrefsTestingLanguage(): SharedPrefsTestingLanguage {
    return when (this) {
        TestingLanguage.ENGLISH -> SharedPrefsTestingLanguage.ENGLISH
    }
}
fun SharedPrefsTestingLanguage.toTestingLanguage(): TestingLanguage {
    return when (this) {
        SharedPrefsTestingLanguage.ENGLISH -> TestingLanguage.ENGLISH
    }
}

fun AppStyle.toSharedPrefsAppStyle(): SharedPrefsAppStyle {
    return when (this) {
        AppStyle.PURPLE -> SharedPrefsAppStyle.PURPLE
        AppStyle.GREEN -> SharedPrefsAppStyle.GREEN
        AppStyle.BLUE -> SharedPrefsAppStyle.BLUE
    }
}
fun SharedPrefsAppStyle.toAppStyle(): AppStyle {
    return when (this) {
        SharedPrefsAppStyle.PURPLE -> AppStyle.PURPLE
        SharedPrefsAppStyle.GREEN -> AppStyle.GREEN
        SharedPrefsAppStyle.BLUE -> AppStyle.BLUE
    }
}