package com.evg.test_essay.domain.usecase

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.test_essay.domain.model.EssayTestData
import com.evg.test_essay.domain.repository.TestEssayRepository

class GetEssayTestData(
    private val testEssayRepository: TestEssayRepository,
) {
    suspend fun invoke(id: Int): ServerResult<EssayTestData, NetworkError> {
        val databaseResponse = testEssayRepository.getEssayTestDataFromDatabase(id = id)
        return if (databaseResponse != null) {
            ServerResult.Success(databaseResponse)
        } else {
            testEssayRepository.getEssayTestDataFromServer(id = id)
        }
    }
}