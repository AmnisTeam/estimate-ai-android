package com.evg.api.domain.mapper

import com.evg.api.CreateEssayTestMutation
import com.evg.api.GetTestDataResponseQuery
import com.evg.api.GetTestStatisticsQuery
import com.evg.api.GetTestsQuery
import com.evg.api.OnTestProgressSubscription
import com.evg.api.domain.model.CreateEssayTestResponse
import com.evg.api.domain.model.GetTestDataResponse
import com.evg.api.domain.model.GetTestStatisticsResponse
import com.evg.api.domain.model.GetTestsResponse
import com.evg.api.domain.model.OnTestProgressResponse
import com.evg.api.domain.model.TestResponse

fun GetTestsQuery.GetTestsResponse.toTestResponses(): GetTestsResponse {
    return GetTestsResponse(
        code = code,
        count = count,
        pages = pages,
        next = next,
        prev = prev,
        tests = tests.mapNotNull { topic ->
            when {
                topic.onReadyTest != null -> TestResponse.OnReadyTestResponse(
                    id = topic.onReadyTest.id,
                    title = topic.onReadyTest.title,
                    type = topic.onReadyTest.type,
                    description = topic.onReadyTest.description,
                    score = topic.onReadyTest.score,
                    createdAt = topic.onReadyTest.createdAt,
                )
                topic.onLoadingTest != null -> TestResponse.OnLoadingTestResponse(
                    id = topic.onLoadingTest.id,
                    type = topic.onLoadingTest.type,
                    queue = topic.onLoadingTest.queue,
                    progress = topic.onLoadingTest.progress,
                    createdAt = topic.onLoadingTest.createdAt,
                )
                topic.onErrorTest != null -> TestResponse.OnErrorTestResponse(
                    id = topic.onErrorTest.id,
                    createdAt = topic.onErrorTest.createdAt,
                )
                else -> null
            }
        }
    )
}

fun OnTestProgressSubscription.OnTestProgressResponse.toOnTestProgressResponse(): OnTestProgressResponse {
    return OnTestProgressResponse(
        code = this.code,
        tests = this.tests.mapNotNull { topic ->
            when {
                topic.onReadyTest != null -> TestResponse.OnReadyTestResponse(
                    id = topic.onReadyTest.id,
                    title = topic.onReadyTest.title,
                    type = topic.onReadyTest.type,
                    description = topic.onReadyTest.description,
                    score = topic.onReadyTest.score,
                    createdAt = topic.onReadyTest.createdAt,
                )
                topic.onLoadingTest != null -> TestResponse.OnLoadingTestResponse(
                    id = topic.onLoadingTest.id,
                    type = topic.onLoadingTest.type,
                    queue = topic.onLoadingTest.queue,
                    progress = topic.onLoadingTest.progress,
                    createdAt = topic.onLoadingTest.createdAt,
                )
                topic.onErrorTest != null -> TestResponse.OnErrorTestResponse(
                    id = topic.onErrorTest.id,
                    createdAt = topic.onErrorTest.createdAt,
                )
                else -> null
            }
        }
    )
}

fun CreateEssayTestMutation.CreateEssayTestResponse.toCreateEssayTestResponse(): CreateEssayTestResponse {
    return CreateEssayTestResponse(
        code = code,
    )
}


fun GetTestDataResponseQuery.GetTestDataResponse.toGetTestDataResponse(): GetTestDataResponse {
    return when {
        this.testData.onEssayTest != null -> {
            GetTestDataResponse.EssayTest(
                id = this.testData.onEssayTest.id,
                essay = this.testData.onEssayTest.essay
            )
        }
        else -> throw IllegalArgumentException("Response does not contain TestData")
    }
}

fun GetTestStatisticsQuery.GetTestStatisticsResponse.toGetTestStatisticsResponse(): GetTestStatisticsResponse {
    return GetTestStatisticsResponse(
        code = this.code,
        testStatistics = this.testStatistics.map {
            TestResponse.OnReadyTestResponse(
                id = it.id,
                title = it.title,
                type = it.type,
                description = it.description,
                score = it.score,
                createdAt = it.createdAt,
            )
        }
    )
}