package com.evg.test_essay.domain.repository

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.test_essay.domain.model.CreateEssayTest

interface TestEssayRepository {
    suspend fun createEssayTest(data: CreateEssayTest): ServerResult<Unit, NetworkError>
}