package com.evg.tests_list.domain.repository

import androidx.paging.PagingData
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestType
import kotlinx.coroutines.flow.Flow

interface TestsListRepository {
    suspend fun getAllTestsByPage(): Flow<PagingData<ServerResult<TestType, NetworkError>>>
    suspend fun connectTestProgress(): ServerResult<Flow<List<TestType>>, NetworkError>
}