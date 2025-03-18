package com.evg.account.di

import com.evg.account.data.repository.AccountRepositoryImpl
import com.evg.account.domain.repository.AccountRepository
import com.evg.account.presentation.mvi.AccountViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val accountModule = module {
    single<AccountRepository> { AccountRepositoryImpl(sharedPrefsRepository = get()) }
    viewModel { AccountViewModel(accountRepository = get()) }
    /*single { LoginUseCases(
        loginUseCase = get(),
        saveUserTokenUseCase = get(),
    ) }
    single { LoginUseCase(loginRepository = get()) }
    single { SaveUserTokenUseCase(sharedPrefsRepository = get()) }*/
}