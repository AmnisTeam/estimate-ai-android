package com.evg.account.domain.model

import androidx.annotation.StringRes
import com.evg.resource.R

enum class AppLanguage(@StringRes val labelRes: Int) {
    USER(R.string.system),
    ENGLISH(R.string.english),
    RUSSIAN(R.string.russian)
}