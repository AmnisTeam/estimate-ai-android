package com.evg.statistics.di

import com.evg.statistics.data.repository.StatisticsRepositoryImpl
import com.evg.statistics.domain.repository.StatisticsRepository
import com.evg.statistics.domain.usecase.GetAllStatisticsUseCase
import com.evg.statistics.domain.usecase.StatisticsUseCases
import com.evg.statistics.presentation.mvi.StatisticsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val statisticsModule = module {
    single<StatisticsRepository> {
        StatisticsRepositoryImpl(
            apiRepository = get(),
            databaseRepository = get(),
        )
    }
    viewModel {
        StatisticsViewModel(
            statisticsUseCases = get(),
        )
    }
    single {
        StatisticsUseCases(
            getTestStatisticsUseCase = get(),
        )
    }
    single { GetAllStatisticsUseCase(statisticsRepository = get()) }
}