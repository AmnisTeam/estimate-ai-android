package com.evg.account.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.AppStyle
import com.evg.account.domain.model.AppTheme
import com.evg.account.domain.model.TestingLanguage
import com.evg.account.domain.repository.AccountRepository
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class AccountViewModel(
    private val accountRepository: AccountRepository,
): ContainerHost<AccountState, AccountSideEffect>, ViewModel() {
    override val container = container<AccountState, AccountSideEffect>(AccountState(
        user = accountRepository.getUser(),
        appTheme = accountRepository.getAppTheme(),
        appLanguage = accountRepository.getAppLanguage(),
        testingLanguage = accountRepository.getTestingLanguage(),
    ))

    fun dispatch(action: AccountAction) {
        when (action) {
            AccountAction.Logout -> accountRepository.logout()
            is AccountAction.SaveAppLanguage -> saveAppLanguage(language = action.language)
            is AccountAction.SaveAppTheme -> saveAppTheme(theme = action.theme)
            is AccountAction.SaveTestingLanguage -> saveTestingLanguage(language =action.language)
            is AccountAction.SaveAppStyle -> saveAppStyle(style = action.style)
        }
    }

    private fun saveAppLanguage(language: AppLanguage) = intent {
        accountRepository.saveAppLanguage(language)
        reduce { state.copy(appLanguage = language) }
    }

    private fun saveAppTheme(theme: AppTheme) = intent {
        accountRepository.saveAppTheme(theme)
        reduce { state.copy(appTheme = theme) }
    }

    private fun saveTestingLanguage(language: TestingLanguage) = intent {
        accountRepository.saveTestingLanguage(language)
        reduce { state.copy(testingLanguage = language) }
    }

    private fun saveAppStyle(style: AppStyle) = intent {
        accountRepository.saveAppStyle(style)
    }
}