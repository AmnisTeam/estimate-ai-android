package com.evg.tests_list.presentation.model

sealed class TestState {
    data class FinishedTest(val finishedTest: com.evg.tests_list.domain.model.FinishedTest) : TestState()
    data class LoadingTest(val loadingTest: com.evg.tests_list.domain.model.LoadingTest) : TestState()
}
