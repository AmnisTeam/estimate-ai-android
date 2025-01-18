package com.evg.statistics.domain.usecase

import com.evg.statistics.domain.repository.StatisticsRepository
import kotlinx.coroutines.flow.Flow

class GetAllStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) {
    suspend fun invoke()/*: Flow<List<Statistics>>*/ {
        //return statisticsRepository.getAllStatistics()
    }
}