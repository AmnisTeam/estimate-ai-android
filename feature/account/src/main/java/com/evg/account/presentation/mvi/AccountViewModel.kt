package com.evg.account.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.account.domain.repository.AccountRepository
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class AccountViewModel(
    private val accountRepository: AccountRepository,
): ContainerHost<AccountState, AccountSideEffect>, ViewModel() {
    override val container = container<AccountState, AccountSideEffect>(AccountState(
        appTheme = accountRepository.getAppTheme(),
        appLanguage = accountRepository.getAppLanguage(),
        testingLanguage = accountRepository.getTestingLanguage(),
    ))

    fun dispatch(action: AccountAction) {
        when (action) {
            AccountAction.Logout -> accountRepository.logout()
            is AccountAction.SaveAppLanguage -> accountRepository.saveAppLanguage(language = action.language)
            is AccountAction.SaveAppTheme -> accountRepository.saveAppTheme(theme = action.theme)
            is AccountAction.SaveTestingLanguage -> accountRepository.saveTestingLanguage(language = action.language)
            is AccountAction.SaveAppStyle -> accountRepository.saveAppStyle(style = action.style)
        }
    }
}