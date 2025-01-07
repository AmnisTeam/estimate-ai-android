package com.evg.api.domain.mapper

import com.evg.api.CreateEssayTestMutation
import com.evg.api.GetTestsQuery
import com.evg.api.OnTestProgressSubscription
import com.evg.api.domain.model.CreateEssayTestResponse
import com.evg.api.domain.model.GetTestsResponse
import com.evg.api.domain.model.OnTestProgressResponse
import com.evg.api.domain.model.TestResponse
import com.evg.database.domain.model.ErrorTestTypeDBO
import com.evg.database.domain.model.LoadingTestTypeDBO
import com.evg.database.domain.model.ReadyTestTypeDBO
import com.evg.database.domain.model.TestTypeDBO

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

fun TestResponse.toTestTypeDBO(): TestTypeDBO {
    return TestTypeDBO().apply {
        when (this@toTestTypeDBO) {
            is TestResponse.OnReadyTestResponse -> {
                readyTestTypeDBO = ReadyTestTypeDBO().apply {
                    id = this@toTestTypeDBO.id
                    title = this@toTestTypeDBO.title
                    type = this@toTestTypeDBO.type
                    description = this@toTestTypeDBO.description
                    level = this@toTestTypeDBO.level
                }
            }
            is TestResponse.OnLoadingTestResponse -> {
                loadingTestTypeDBO = LoadingTestTypeDBO().apply {
                    id = this@toTestTypeDBO.id
                    type = this@toTestTypeDBO.type
                    queue = this@toTestTypeDBO.queue
                    progress = this@toTestTypeDBO.progress
                }
            }
            is TestResponse.OnErrorTestResponse -> {
                errorTestTypeDBO = ErrorTestTypeDBO().apply {
                    id = this@toTestTypeDBO.id
                }
            }
        }
    }
}
