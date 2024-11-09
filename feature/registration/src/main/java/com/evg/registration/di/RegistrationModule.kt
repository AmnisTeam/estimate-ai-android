package com.evg.registration.di

import com.evg.registration.data.repository.RegistrationRepositoryImpl
import com.evg.registration.domain.repository.RegistrationRepository
import com.evg.registration.domain.usecase.RegistrationUseCase
import com.evg.registration.domain.usecase.RegistrationUseCases
import com.evg.registration.presentation.mvi.RegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    single<RegistrationRepository> { RegistrationRepositoryImpl(apiRepository = get()) }
    viewModel { RegistrationViewModel(registrationUseCases = get()) }
    factory { RegistrationUseCases(registrationUseCase = get()) }
    factory { RegistrationUseCase(registrationRepository = get()) }
}