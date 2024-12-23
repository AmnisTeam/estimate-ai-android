package com.evg.tests_list.domain.usecase

import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.repository.TestsListRepository
import kotlinx.coroutines.flow.Flow

class ConnectTestProgressUseCase(
    private val testsListRepository: TestsListRepository,
) {
    suspend fun invoke(): ServerResult<Flow<List<TestType>>, NetworkError> {
        return testsListRepository.connectTestProgress()
    }
}