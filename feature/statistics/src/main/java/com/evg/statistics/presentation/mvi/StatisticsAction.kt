package com.evg.statistics.presentation.mvi

import com.evg.statistics.presentation.model.DateRange

sealed class StatisticsAction {
    data class GetAllStatistics(val dateRange: DateRange): StatisticsAction()
    data class GetStatisticsInRange(val dateRange: DateRange): StatisticsAction()
}