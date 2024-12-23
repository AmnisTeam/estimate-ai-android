package com.evg.api.domain.model

data class OnTestProgressResponse(
    val code: Int,
    val tests: List<TestResponse>,
)