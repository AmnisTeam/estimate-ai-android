package com.evg.account.domain.model

import androidx.annotation.StringRes
import com.evg.resource.R

enum class TestingLanguage(@StringRes val labelRes: Int) {
    ENGLISH(R.string.english),
    SPANISH(R.string.spanish),
}