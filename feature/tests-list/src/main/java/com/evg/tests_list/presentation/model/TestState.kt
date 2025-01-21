package com.evg.tests_list.presentation.model

import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestLevelColors

sealed class TestState {
    data class FinishedTest(
        val id: Int,
        val title: String,
        val icon: TestIcons,
        val description: String,
        val levelColor: TestLevelColors,
        val createdAt: Long,
    ) : TestState()
    data class LoadingTest(
        val id: Int,
        val icon: TestIcons,
        val queue: Int,
        val progress: Int,
        val createdAt: Long,
    ) : TestState()
    data class ErrorTest(
        val id: Int,
        val createdAt: Long,
    ) : TestState()
}
