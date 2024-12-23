package com.evg.tests_list.domain.model

sealed class TestType {
    data class OnReadyTestType(
        val id: Int,
        val title: String,
        val type: String,
        val status: String,
        val level: String,
        val createdAt: Int,
    ) : TestType()
    data class OnLoadingTestType(
        val id: Int,
        val progress: Int,
        val queue: Int,
        val createdAt: Int,
    ) : TestType()
    data class OnErrorTestType(
        val id: Int,
    ) : TestType()
}
