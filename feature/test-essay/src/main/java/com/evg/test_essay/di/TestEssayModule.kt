package com.evg.test_essay.di

import com.evg.test_essay.data.repository.TestEssayRepositoryImpl
import com.evg.test_essay.domain.repository.TestEssayRepository
import com.evg.test_essay.domain.usecase.GetEssayTestData
import com.evg.test_essay.domain.usecase.SendTestToServerUseCase
import com.evg.test_essay.domain.usecase.TestEssayUseCases
import com.evg.test_essay.presentation.mvi.TestEssayViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val testEssayModule = module {
    single<TestEssayRepository> { TestEssayRepositoryImpl(
        apiRepository = get(),
        databaseRepository = get(),
    ) }
    viewModel { (testId: Int?) -> TestEssayViewModel(testEssayUseCases = get(), testId = testId) }
    single {
        TestEssayUseCases(
            sendTestToServerUseCase = get(),
            getEssayTestData = get(),
        )
    }
    single { SendTestToServerUseCase(testEssayRepository = get()) }
    single { GetEssayTestData(testEssayRepository = get()) }
}