package com.evg.tests_list.domain.model

sealed class TestType {
    data class OnReadyTestType(
        val id: Int,
        val title: String,
        val type: String,
        val description: String,
        val level: String,
    ) : TestType()
    data class OnLoadingTestType(
        val id: Int,
        val type: String,
        val queue: Int,
        val progress: Int,
    ) : TestType()
    data class OnErrorTestType(
        val id: Int,
    ) : TestType()
}
