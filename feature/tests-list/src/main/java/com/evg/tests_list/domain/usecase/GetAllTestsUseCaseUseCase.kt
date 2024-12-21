package com.evg.tests_list.domain.usecase

import androidx.paging.PagingData
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.repository.TestsListRepository
import kotlinx.coroutines.flow.Flow


class GetAllTestsUseCaseUseCase(
    private val testsListRepository: TestsListRepository,
) {
    suspend fun invoke(): Flow<PagingData<ServerResult<TestType, NetworkError>>> {
        return testsListRepository.getAllTestsByPage()
    }
}