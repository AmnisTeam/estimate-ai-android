package com.evg.tests_list.di

import androidx.work.WorkManager
import com.evg.tests_list.data.repository.TestsListRepositoryImpl
import com.evg.tests_list.domain.repository.TestsListRepository
import com.evg.tests_list.domain.usecase.ConnectTestProgressUseCase
import com.evg.tests_list.domain.usecase.GetAllTestsUseCaseUseCase
import com.evg.tests_list.domain.usecase.TestsListUseCases
import com.evg.tests_list.presentation.mvi.TestsListViewModel
import com.evg.tests_list.presentation.service.TestStatusService
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val testsListModule = module {
    single<TestsListRepository> {
        TestsListRepositoryImpl(
            apiRepository = get(),
            testPageSourceRemote = get(),
            testPageSourceLocal = get(),
        )
    }
    viewModel {
        TestsListViewModel(
            testsListUseCases = get(),
            testProgressWorker = get(),
        )
    }
    single {
        TestsListUseCases(
            getAllTestsUseCaseUseCase = get(),
            connectTestProgressUseCase = get(),
        )
    }
    single { GetAllTestsUseCaseUseCase(testsListRepository = get()) }
    single { ConnectTestProgressUseCase(testsListRepository = get()) }

    single { WorkManager.getInstance(get()) }
}