package com.evg.account.domain.model

import androidx.annotation.StringRes
import com.evg.resource.R

enum class TestingLanguage(@StringRes val labelRes: Int) {
    CHINESE(R.string.chinese),
    ENGLISH(R.string.english),
    FRENCH(R.string.french),
    GERMAN(R.string.german),
    HINDI(R.string.hindi),
    ITALIAN(R.string.italian),
    JAPANESE(R.string.japanese),
    KOREAN(R.string.korean),
    PORTUGUESE(R.string.portuguese),
    RUSSIAN(R.string.russian),
    SPANISH(R.string.spanish),
    TURKISH(R.string.turkish);
}