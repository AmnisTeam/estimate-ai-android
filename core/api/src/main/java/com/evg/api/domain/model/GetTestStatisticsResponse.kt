package com.evg.api.domain.model

data class GetTestStatisticsResponse(
    val code: Int,
    val testStatistics: List<TestResponse.OnReadyTestResponse>,
)