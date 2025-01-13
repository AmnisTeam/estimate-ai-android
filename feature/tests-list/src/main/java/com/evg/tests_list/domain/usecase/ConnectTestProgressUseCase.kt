package com.evg.tests_list.domain.usecase

import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.repository.TestsListRepository
import kotlinx.coroutines.flow.Flow

class ConnectTestProgressUseCase(
    private val testsListRepository: TestsListRepository,
) {
    suspend fun invoke(): Flow<List<TestType>> {
        return testsListRepository.connectTestProgress()
    }
}