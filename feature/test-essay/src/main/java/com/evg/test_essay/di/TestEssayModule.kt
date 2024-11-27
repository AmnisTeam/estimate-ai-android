package com.evg.test_essay.di

import com.evg.test_essay.data.repository.TestEssayRepositoryImpl
import com.evg.test_essay.domain.repository.TestEssayRepository
import com.evg.test_essay.domain.usecase.SendTestToServerUseCase
import com.evg.test_essay.domain.usecase.TestEssayUseCases
import com.evg.test_essay.presentation.mvi.TestEssayViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val testEssayModule = module {
    single<TestEssayRepository> { TestEssayRepositoryImpl(/*apiRepository = get()*/) }
    viewModel { TestEssayViewModel(testEssayUseCases = get()) }
    factory { TestEssayUseCases(sendTestToServerUseCase = get()) }
    factory { SendTestToServerUseCase(testEssayRepository = get()) }
}