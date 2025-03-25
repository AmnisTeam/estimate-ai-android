package com.evg.account.domain.model

import androidx.annotation.StringRes
import com.evg.resource.R

enum class AppLanguage(@StringRes val labelRes: Int, val code: String) {
    USER(R.string.system, ""),
    ENGLISH(R.string.english, "en"),
    RUSSIAN(R.string.russian, "ru"),
}