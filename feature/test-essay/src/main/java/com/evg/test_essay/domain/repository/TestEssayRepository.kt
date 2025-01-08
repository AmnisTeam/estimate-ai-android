package com.evg.test_essay.domain.repository

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.test_essay.domain.model.EssayTestData

interface TestEssayRepository {
    suspend fun createEssayTest(data: EssayTestData): ServerResult<Unit, NetworkError>
    fun getEssayTestDataFromDatabase(id: Int): EssayTestData?
    suspend fun getEssayTestDataFromServer(id: Int): ServerResult<EssayTestData, NetworkError>
}