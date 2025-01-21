package com.evg.statistics.presentation.mvi

import com.evg.statistics.presentation.model.StatisticsUI
import kotlinx.coroutines.flow.MutableStateFlow

data class StatisticsState(
    val isStatisticsLoading: Boolean = false,
    val statistics: MutableStateFlow<StatisticsUI> = MutableStateFlow(
        StatisticsUI(
            frequentLevel = null,
            frequentDayOfWeek = null,
            testStatisticsUI = emptyList()
        )
    )
)