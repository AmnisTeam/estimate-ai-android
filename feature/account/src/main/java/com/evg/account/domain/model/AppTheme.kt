package com.evg.account.domain.model

import androidx.annotation.StringRes
import com.evg.resource.R

enum class AppTheme(@StringRes val labelRes: Int) {
    USER(R.string.system),
    LIGHT(R.string.light),
    DARK(R.string.dark),
}