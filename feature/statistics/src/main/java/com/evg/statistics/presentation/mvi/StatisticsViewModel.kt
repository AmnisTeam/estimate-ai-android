package com.evg.statistics.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evg.statistics.domain.usecase.StatisticsUseCases
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class StatisticsViewModel(
    private val statisticsUseCases: StatisticsUseCases,
): ContainerHost<StatisticsState, StatisticsSideEffect>, ViewModel() {
    override val container = container<StatisticsState, StatisticsSideEffect>(StatisticsState())

    init {
        getAllStatistics()
    }

    fun getAllStatistics() = intent {
        reduce { state.copy(isStatisticsLoading = true) }
        viewModelScope.launch {
            val testSource = statisticsUseCases.getAllStatisticsUseCase.invoke()

            reduce { state.copy(isStatisticsLoading = false) }
        }
    }

}