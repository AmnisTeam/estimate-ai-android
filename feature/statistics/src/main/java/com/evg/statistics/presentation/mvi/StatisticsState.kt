package com.evg.statistics.presentation.mvi

import com.evg.statistics.presentation.model.Statistic
import kotlinx.coroutines.flow.MutableStateFlow

data class StatisticsState(
    val isStatisticsLoading: Boolean = false,
    val statistics: MutableStateFlow<List<Statistic>> = MutableStateFlow(emptyList())
)