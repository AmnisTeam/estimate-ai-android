package com.evg.statistics.presentation.mvi

import com.evg.api.domain.utils.NetworkError

sealed class StatisticsSideEffect {
    data class StatisticsFail(val error: NetworkError) : StatisticsSideEffect()
}