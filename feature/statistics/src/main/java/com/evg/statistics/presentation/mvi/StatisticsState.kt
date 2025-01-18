package com.evg.statistics.presentation.mvi

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import kotlinx.coroutines.flow.MutableStateFlow

data class StatisticsState(
    val isStatisticsLoading: Boolean = false,
    //val statistics: MutableStateFlow<Statistics> = MutableStateFlow()
)