package com.evg.test_essay.data.repository

import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.test_essay.domain.mapper.toCreateEssayTestDTO
import com.evg.test_essay.domain.model.CreateEssayTest
import com.evg.test_essay.domain.repository.TestEssayRepository

class TestEssayRepositoryImpl(
    private val apiRepository: ApiRepository,
): TestEssayRepository {
    override suspend fun createEssayTest(data: CreateEssayTest): ServerResult<Unit, NetworkError> {
        return apiRepository.createEssayTest(data = data.toCreateEssayTestDTO())
    }
}