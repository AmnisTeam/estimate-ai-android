package com.evg.api.domain.mapper

import com.evg.api.GetTestsQuery
import com.evg.api.OnTestProgressSubscription
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
                    status = topic.onReadyTest.status,
                    level = topic.onReadyTest.level,
                    createdAt = topic.onReadyTest.createdAt
                )
                topic.onLoadingTest != null -> TestResponse.OnLoadingTestResponse(
                    id = topic.onLoadingTest.id,
                    progress = topic.onLoadingTest.progress,
                    queue = topic.onLoadingTest.queue,
                    createdAt = topic.onLoadingTest.createdAt
                )
                topic.onErrorTest != null -> TestResponse.OnErrorTestResponse(
                    id = topic.onErrorTest.id,
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
                    status = topic.onReadyTest.status,
                    level = topic.onReadyTest.level,
                    createdAt = topic.onReadyTest.createdAt
                )
                topic.onLoadingTest != null -> TestResponse.OnLoadingTestResponse(
                    id = topic.onLoadingTest.id,
                    progress = topic.onLoadingTest.progress,
                    queue = topic.onLoadingTest.queue,
                    createdAt = topic.onLoadingTest.createdAt
                )
                topic.onErrorTest != null -> TestResponse.OnErrorTestResponse(
                    id = topic.onErrorTest.id,
                )
                else -> null
            }
        }
    )
}