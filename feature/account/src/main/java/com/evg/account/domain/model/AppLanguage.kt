package com.evg.account.domain.model

import androidx.annotation.StringRes
import com.evg.resource.R

enum class AppLanguage(@StringRes val labelRes: Int, val code: String) {
    USER(R.string.system, ""),
    ENGLISH(R.string.english, "en"),
    CHINESE(R.string.chinese, "zh"),
    SPANISH(R.string.spanish, "es"),
    FRENCH(R.string.french, "fr"),
    RUSSIAN(R.string.russian, "ru"),
    PORTUGUESE(R.string.portuguese, "pt"),
    GERMAN(R.string.german, "de"),
    HINDI(R.string.hindi, "hi"),
    JAPANESE(R.string.japanese, "ja"),
    TURKISH(R.string.turkish, "tr"),
    KOREAN(R.string.korean, "ko"),
    ITALIAN(R.string.italian, "it");
}