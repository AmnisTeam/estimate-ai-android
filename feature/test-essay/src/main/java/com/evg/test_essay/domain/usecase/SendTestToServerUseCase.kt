package com.evg.test_essay.domain.usecase

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.test_essay.domain.model.CreateEssayTest
import com.evg.test_essay.domain.repository.TestEssayRepository


class SendTestToServerUseCase(
    private val testEssayRepository: TestEssayRepository,
) {
    suspend fun invoke(data: CreateEssayTest): ServerResult<Unit, NetworkError> {
        return testEssayRepository.createEssayTest(data = data)
    }
}