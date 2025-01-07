package com.evg.tests_list.domain.model

import androidx.paging.PagingData
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import kotlinx.coroutines.flow.Flow

sealed class TestSource {
    data class Network(val data: Flow<PagingData<ServerResult<TestType, NetworkError>>>) : TestSource()
    data class Local(val data: Flow<PagingData<TestType>>) : TestSource()
}
