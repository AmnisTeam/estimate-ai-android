package com.evg.statistics.domain.usecase

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.statistics.domain.model.TestStatistics
import com.evg.statistics.domain.repository.StatisticsRepository

class GetAllStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) {
    suspend fun invoke(): ServerResult<List<TestStatistics>, NetworkError> {
        return if (statisticsRepository.isInternetAvailable()) {
            statisticsRepository.getTestStatisticsRemote()
        } else {
            ServerResult.Success(statisticsRepository.getTestStatisticsLocal())
        }
    }
}