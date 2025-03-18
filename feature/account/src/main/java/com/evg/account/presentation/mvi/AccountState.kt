package com.evg.account.presentation.mvi

import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.AppTheme
import com.evg.account.domain.model.TestingLanguage

data class AccountState(
    val appLanguage: AppLanguage,
    val appTheme: AppTheme,
    val testingLanguage: TestingLanguage,
)