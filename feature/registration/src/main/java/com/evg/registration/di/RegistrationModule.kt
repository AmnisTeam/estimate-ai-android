package com.evg.registration.di

import com.evg.registration.data.repository.RegistrationRepositoryImpl
import com.evg.registration.domain.repository.RegistrationRepository
import com.evg.registration.domain.usecase.RegistrationUseCase
import com.evg.registration.domain.usecase.RegistrationUseCases
import org.koin.dsl.module

val registrationModule = module {
    single<RegistrationRepository> { RegistrationRepositoryImpl() }
    factory { RegistrationUseCases(registrationUseCase = get()) }
    factory { RegistrationUseCase(registrationRepository = get()) }
}