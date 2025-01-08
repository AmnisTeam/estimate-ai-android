package com.evg.api.domain.model

sealed class GetTestDataResponse {
    data class EssayTest(
        val id: Int,
        val essay: String,
    ) : GetTestDataResponse()
}