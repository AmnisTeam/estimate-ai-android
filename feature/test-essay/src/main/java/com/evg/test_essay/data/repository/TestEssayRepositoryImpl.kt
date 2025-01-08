package com.evg.test_essay.data.repository

import com.evg.api.domain.model.GetTestDataResponse
import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.database.domain.repository.DatabaseRepository
import com.evg.test_essay.domain.mapper.toCreateEssayTestDTO
import com.evg.test_essay.domain.mapper.toEssayTestData
import com.evg.test_essay.domain.model.EssayTestData
import com.evg.test_essay.domain.repository.TestEssayRepository

class TestEssayRepositoryImpl(
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository,
): TestEssayRepository {
    override suspend fun createEssayTest(data: EssayTestData): ServerResult<Unit, NetworkError> {
        return apiRepository.createEssayTest(data = data.toCreateEssayTestDTO())
    }

    override fun getEssayTestDataFromDatabase(id: Int): EssayTestData? {
        return databaseRepository.getTestData(id = id)?.essayTestDBO.toEssayTestData()
    }

    override suspend fun getEssayTestDataFromServer(id: Int): ServerResult<EssayTestData, NetworkError> {
        return when(val result = apiRepository.getTestDataResponse(id = id)) {
            is ServerResult.Success -> {
                val data = result.data
                return if (data is GetTestDataResponse.EssayTest) {
                    ServerResult.Success(data.toEssayTestData())
                } else {
                    ServerResult.Error(NetworkError.UNKNOWN)
                }
            }
            is ServerResult.Error -> ServerResult.Error(result.error)
        }
    }
}