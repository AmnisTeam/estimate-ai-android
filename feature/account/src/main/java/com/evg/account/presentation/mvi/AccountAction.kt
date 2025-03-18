package com.evg.account.presentation.mvi

import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.AppTheme
import com.evg.account.domain.model.TestingLanguage

sealed class AccountAction {
    data object Logout : AccountAction()
    data class SaveAppLanguage(val language: AppLanguage): AccountAction()
    data class SaveAppTheme(val theme: AppTheme): AccountAction()
    data class SaveTestingLanguage(val language: TestingLanguage): AccountAction()
}