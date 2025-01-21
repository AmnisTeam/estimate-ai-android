package com.evg.statistics.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evg.api.domain.utils.ServerResult
import com.evg.statistics.domain.model.TestStatistics
import com.evg.statistics.domain.usecase.StatisticsUseCases
import com.evg.statistics.presentation.mapper.toStatisticsUI
import com.evg.statistics.presentation.model.DateTile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class StatisticsViewModel(
    private val statisticsUseCases: StatisticsUseCases,
): ContainerHost<StatisticsState, StatisticsSideEffect>, ViewModel() {
    override val container = container<StatisticsState, StatisticsSideEffect>(StatisticsState())

    companion object {
         val defaultSelect = DateTile.Dates.WEEK
    }

    private val statisticsFull: MutableStateFlow<List<TestStatistics>> = MutableStateFlow(emptyList())

    init {
        getAllStatistics()
    }

    private fun getAllStatistics() = intent {
        reduce { state.copy(isStatisticsLoading = true) }
        viewModelScope.launch {
            when (val response = statisticsUseCases.getTestStatisticsUseCase.invoke()) {
                is ServerResult.Success -> {
                    statisticsFull.value = response.data
                    getStatisticsInRange(defaultSelect.toTimeRange())
                }
                is ServerResult.Error -> {
                    postSideEffect(StatisticsSideEffect.StatisticsFail(error = response.error))
                }
            }

            reduce { state.copy(isStatisticsLoading = false) }
        }
    }

    fun getStatisticsInRange(time: Pair<Long, Long>) = intent {
        val filteredStatistics = statisticsFull.value
            .filter { it.createdAt in time.first..time.second }
            .toStatisticsUI()
        container.stateFlow.value.statistics.value = filteredStatistics
    }

}