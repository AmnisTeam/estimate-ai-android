package com.evg.statistics.data.repository

import com.evg.api.domain.utils.mapData
import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.database.domain.repository.DatabaseRepository
import com.evg.statistics.domain.mapper.toTestStatistics
import com.evg.statistics.domain.model.TestStatistics
import com.evg.statistics.domain.repository.StatisticsRepository

class StatisticsRepositoryImpl(
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository,
): StatisticsRepository {
    override suspend fun getTestStatisticsRemote(): ServerResult<List<TestStatistics>, NetworkError> {
         return apiRepository.getTestStatisticsResponse().mapData {
            it.toTestStatistics()
        }
    }

    override suspend fun getTestStatisticsLocal(): List<TestStatistics> {
        return databaseRepository.getTestStatistics().toTestStatistics()
    }

    override fun isInternetAvailable() = apiRepository.isInternetAvailable()
}