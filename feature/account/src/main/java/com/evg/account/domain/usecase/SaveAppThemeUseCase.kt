package com.evg.account.domain.usecase

import com.evg.account.domain.repository.AccountRepository

class SaveAppThemeUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend fun invoke() {
        //accountRepository.saveAppTheme()
    }
}