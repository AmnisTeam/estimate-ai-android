package com.evg.password_reset.di

import com.evg.password_reset.data.repository.PasswordResetRepositoryImpl
import com.evg.password_reset.domain.repository.PasswordResetRepository
import com.evg.password_reset.domain.usecase.PasswordResetUseCase
import com.evg.password_reset.domain.usecase.PasswordResetUseCases
import com.evg.password_reset.presentation.mvi.PasswordResetViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val passwordResetModule = module {
    single<PasswordResetRepository> { PasswordResetRepositoryImpl(apiRepository = get()) }
    viewModel { PasswordResetViewModel(passwordResetUseCases = get()) }
    single { PasswordResetUseCases(passwordResetUseCase = get()) }
    single { PasswordResetUseCase(passwordResetRepository = get()) }
}