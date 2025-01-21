package com.evg.api.domain.model

sealed class TestResponse {
    data class OnReadyTestResponse(
        val id: Int,
        val title: String,
        val type: String,
        val description: String,
        val score: Int,
        val createdAt: Long,
    ) : TestResponse()
    data class OnLoadingTestResponse(
        val id: Int,
        val type: String,
        val queue: Int,
        val progress: Int,
        val createdAt: Long,
    ) : TestResponse()
    data class OnErrorTestResponse(
        val id: Int,
        val createdAt: Long,
    ) : TestResponse()
}
