package com.evg.api.domain.model

data class GetTestsResponse(
    val code: Int,
    val count: Int,
    val pages: Int,
    val next: Int?,
    val prev: Int?,
    val tests: List<TestResponse>,
)