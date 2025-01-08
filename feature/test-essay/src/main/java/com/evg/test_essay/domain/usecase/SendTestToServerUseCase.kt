package com.evg.test_essay.domain.usecase

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.test_essay.domain.model.EssayTestData
import com.evg.test_essay.domain.repository.TestEssayRepository


class SendTestToServerUseCase(
    private val testEssayRepository: TestEssayRepository,
) {
    suspend fun invoke(data: EssayTestData): ServerResult<Unit, NetworkError> {
        return testEssayRepository.createEssayTest(data = data)
    }
}