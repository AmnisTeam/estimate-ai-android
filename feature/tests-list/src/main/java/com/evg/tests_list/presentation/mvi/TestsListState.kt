package com.evg.tests_list.presentation.mvi

import androidx.paging.PagingData
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestType
import kotlinx.coroutines.flow.MutableStateFlow

data class TestsListState(
    val isTestsLoading: Boolean = false,
    val tests: MutableStateFlow<PagingData<ServerResult<TestType, NetworkError>>> = MutableStateFlow(PagingData.empty())
)