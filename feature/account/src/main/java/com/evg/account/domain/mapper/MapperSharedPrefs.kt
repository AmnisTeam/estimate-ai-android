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
        AppLanguage.USER        -> SharedPrefsAppLanguage.USER
        AppLanguage.CHINESE     -> SharedPrefsAppLanguage.CHINESE
        AppLanguage.ENGLISH     -> SharedPrefsAppLanguage.ENGLISH
        AppLanguage.FRENCH      -> SharedPrefsAppLanguage.FRENCH
        AppLanguage.GERMAN      -> SharedPrefsAppLanguage.GERMAN
        AppLanguage.HINDI       -> SharedPrefsAppLanguage.HINDI
        AppLanguage.ITALIAN     -> SharedPrefsAppLanguage.ITALIAN
        AppLanguage.JAPANESE    -> SharedPrefsAppLanguage.JAPANESE
        AppLanguage.KOREAN      -> SharedPrefsAppLanguage.KOREAN
        AppLanguage.PORTUGUESE  -> SharedPrefsAppLanguage.PORTUGUESE
        AppLanguage.RUSSIAN     -> SharedPrefsAppLanguage.RUSSIAN
        AppLanguage.SPANISH     -> SharedPrefsAppLanguage.SPANISH
        AppLanguage.TURKISH     -> SharedPrefsAppLanguage.TURKISH
    }
}
fun SharedPrefsAppLanguage.toAccountAppLanguage(): AppLanguage {
    return when (this) {
        SharedPrefsAppLanguage.USER        -> AppLanguage.USER
        SharedPrefsAppLanguage.CHINESE     -> AppLanguage.CHINESE
        SharedPrefsAppLanguage.ENGLISH     -> AppLanguage.ENGLISH
        SharedPrefsAppLanguage.FRENCH      -> AppLanguage.FRENCH
        SharedPrefsAppLanguage.GERMAN      -> AppLanguage.GERMAN
        SharedPrefsAppLanguage.HINDI       -> AppLanguage.HINDI
        SharedPrefsAppLanguage.ITALIAN     -> AppLanguage.ITALIAN
        SharedPrefsAppLanguage.JAPANESE    -> AppLanguage.JAPANESE
        SharedPrefsAppLanguage.KOREAN      -> AppLanguage.KOREAN
        SharedPrefsAppLanguage.PORTUGUESE  -> AppLanguage.PORTUGUESE
        SharedPrefsAppLanguage.RUSSIAN     -> AppLanguage.RUSSIAN
        SharedPrefsAppLanguage.SPANISH     -> AppLanguage.SPANISH
        SharedPrefsAppLanguage.TURKISH     -> AppLanguage.TURKISH
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
        TestingLanguage.CHINESE     -> SharedPrefsTestingLanguage.CHINESE
        TestingLanguage.ENGLISH     -> SharedPrefsTestingLanguage.ENGLISH
        TestingLanguage.FRENCH      -> SharedPrefsTestingLanguage.FRENCH
        TestingLanguage.GERMAN      -> SharedPrefsTestingLanguage.GERMAN
        TestingLanguage.HINDI       -> SharedPrefsTestingLanguage.HINDI
        TestingLanguage.ITALIAN     -> SharedPrefsTestingLanguage.ITALIAN
        TestingLanguage.JAPANESE    -> SharedPrefsTestingLanguage.JAPANESE
        TestingLanguage.KOREAN      -> SharedPrefsTestingLanguage.KOREAN
        TestingLanguage.PORTUGUESE  -> SharedPrefsTestingLanguage.PORTUGUESE
        TestingLanguage.RUSSIAN     -> SharedPrefsTestingLanguage.RUSSIAN
        TestingLanguage.SPANISH     -> SharedPrefsTestingLanguage.SPANISH
        TestingLanguage.TURKISH     -> SharedPrefsTestingLanguage.TURKISH
    }
}
fun SharedPrefsTestingLanguage.toTestingLanguage(): TestingLanguage = when (this) {
    SharedPrefsTestingLanguage.CHINESE     -> TestingLanguage.CHINESE
    SharedPrefsTestingLanguage.ENGLISH     -> TestingLanguage.ENGLISH
    SharedPrefsTestingLanguage.FRENCH      -> TestingLanguage.FRENCH
    SharedPrefsTestingLanguage.GERMAN      -> TestingLanguage.GERMAN
    SharedPrefsTestingLanguage.HINDI       -> TestingLanguage.HINDI
    SharedPrefsTestingLanguage.ITALIAN     -> TestingLanguage.ITALIAN
    SharedPrefsTestingLanguage.JAPANESE    -> TestingLanguage.JAPANESE
    SharedPrefsTestingLanguage.KOREAN      -> TestingLanguage.KOREAN
    SharedPrefsTestingLanguage.PORTUGUESE  -> TestingLanguage.PORTUGUESE
    SharedPrefsTestingLanguage.RUSSIAN     -> TestingLanguage.RUSSIAN
    SharedPrefsTestingLanguage.SPANISH     -> TestingLanguage.SPANISH
    SharedPrefsTestingLanguage.TURKISH     -> TestingLanguage.TURKISH
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