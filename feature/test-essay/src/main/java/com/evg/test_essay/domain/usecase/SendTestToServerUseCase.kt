package com.evg.test_essay.domain.usecase

import com.evg.test_essay.domain.repository.TestEssayRepository


class SendTestToServerUseCase(
    private val testEssayRepository: TestEssayRepository,
) {
    suspend fun invoke(): Unit {
        //return testEssayRepository
    }
}