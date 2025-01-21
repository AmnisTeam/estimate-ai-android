package com.evg.statistics.domain.repository

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.statistics.domain.model.TestStatistics

interface StatisticsRepository {
    suspend fun getTestStatisticsRemote(): ServerResult<List<TestStatistics>, NetworkError>
    suspend fun getTestStatisticsLocal(): List<TestStatistics>

    fun isInternetAvailable(): Boolean
}