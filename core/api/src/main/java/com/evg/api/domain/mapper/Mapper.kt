package com.evg.api.domain.mapper

import com.evg.api.CreateEssayTestMutation
import com.evg.api.GetTestsQuery
import com.evg.api.OnTestProgressSubscription
import com.evg.api.domain.model.CreateEssayTestResponse
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
                    level = topic.onReadyTest.level,
                )
                topic.onLoadingTest != null -> TestResponse.OnLoadingTestResponse(
                    id = topic.onLoadingTest.id,
                    type = topic.onLoadingTest.type,
                    queue = topic.onLoadingTest.queue,
                    progress = topic.onLoadingTest.progress,
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
                    description = topic.onReadyTest.description,
                    level = topic.onReadyTest.level,
                )
                topic.onLoadingTest != null -> TestResponse.OnLoadingTestResponse(
                    id = topic.onLoadingTest.id,
                    type = topic.onLoadingTest.type,
                    queue = topic.onLoadingTest.queue,
                    progress = topic.onLoadingTest.progress,
                )
                topic.onErrorTest != null -> TestResponse.OnErrorTestResponse(
                    id = topic.onErrorTest.id,
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