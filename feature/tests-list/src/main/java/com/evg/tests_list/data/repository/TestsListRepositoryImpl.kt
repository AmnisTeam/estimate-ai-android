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
import com.evg.database.data.TestPageSourceLocal
import com.evg.database.domain.repository.DatabaseRepository
import com.evg.tests_list.domain.mapper.toTestType
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.repository.TestsListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TestsListRepositoryImpl(
    private val apiRepository: ApiRepository,
    private val testPageSourceRemote: TestPageSourceRemote,
    private val testPageSourceLocal: TestPageSourceLocal,
): TestsListRepository {
    override suspend fun getAllTestsByPageFromServer(): Flow<PagingData<ServerResult<TestType, NetworkError>>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                initialLoadSize = 20,
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
    }

    override suspend fun getAllTestsFromDatabase(): Flow<PagingData<TestType>> /*Flow<List<TestType>>*/ {
        return Pager(
            PagingConfig(
                pageSize = 10,
                initialLoadSize = 20,
            )
        ) { testPageSourceLocal }
            .flow
            .map { pagingData ->
                pagingData.map { serverResult ->
                    serverResult.toTestType()
                }
            }
        /*return databaseRepository.getAllTests()
            .map { list ->
                list.map { test ->
                    test.toTestType()
                }
            }*/
    }

    override suspend fun connectTestProgress(): ServerResult<Flow<List<TestType>>, NetworkError> {
        return when (val result = apiRepository.onTestProgress()) {
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

    override fun isInternetAvailable() = apiRepository.isInternetAvailable()
}