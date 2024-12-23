package com.evg.api.domain.model

sealed class TestResponse {
    data class OnReadyTestResponse(
        val id: Int,
        val title: String,
        val type: String,
        val status: String,
        val level: String,
        val createdAt: Int,
    ) : TestResponse()
    data class OnLoadingTestResponse(
        val id: Int,
        val progress: Int,
        val queue: Int,
        val createdAt: Int,
    ) : TestResponse()
    data class OnErrorTestResponse(
        val id: Int,
    ) : TestResponse()
}
