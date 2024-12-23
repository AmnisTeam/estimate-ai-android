package com.evg.tests_list.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.evg.api.data.TestPageSourceRemote
import com.evg.api.domain.model.OnTestProgressResponse
import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.mapper.toTestType
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.repository.TestsListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TestsListRepositoryImpl(
    private val apiRepository: ApiRepository,
    private val testPageSourceRemote: TestPageSourceRemote,
): TestsListRepository {
    override suspend fun getAllTestsByPage(): Flow<PagingData<ServerResult<TestType, NetworkError>>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
            )
        ) { testPageSourceRemote }
            .flow
            .map { pagingData ->
                pagingData.map { serverResult ->
                    when (serverResult) {
                        is ServerResult.Success -> ServerResult.Success(serverResult.data.toTestType())
                        is ServerResult.Error -> ServerResult.Error(serverResult.error)
                    }
                }
            }
        /*return if (apiRepository.isInternetAvailable()) {

        }*/
    }

    override suspend fun connectTestProgress(listIds: List<Int>): ServerResult<Flow<List<TestType>>, NetworkError> {
        //return apiRepository.onTestProgress(listIds = listIds)
        return when (val result = apiRepository.onTestProgress(listIds = listIds)) {
            is ServerResult.Success -> {
                ServerResult.Success(
                    result.data.map { response ->
                        response.tests.map {
                            it.toTestType()
                        }
                    }
                )
            }
            is ServerResult.Error -> ServerResult.Error(result.error)
        }
    }
}