package com.evg.tests_list.presentation.mvi

import com.evg.api.domain.utils.NetworkError

sealed class TestsListSideEffect {
    //data class GetAllTestsFail(val error: NetworkError) : TestsListSideEffect()
    data class ConnectTestProgressFail(val error: NetworkError) : TestsListSideEffect()
    data object StartService : TestsListSideEffect()
    //data object StopService : TestsListSideEffect()
}