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
import com.evg.api.fragment.ErrorTestFragment
import com.evg.api.fragment.LoadingTestFragment
import com.evg.api.fragment.ReadyTestFragment

private fun mapReadyTest(readyTest: ReadyTestFragment) = TestResponse.OnReadyTestResponse(
    id = readyTest.id,
    title = readyTest.title,
    type = readyTest.type,
    description = readyTest.description,
    scoreAI = readyTest.scoreAI,
    scoreHuman = readyTest.scoreHuman,
    createdAt = readyTest.createdAt
)

private fun mapLoadingTest(loadingTest: LoadingTestFragment) = TestResponse.OnLoadingTestResponse(
    id = loadingTest.id,
    type = loadingTest.type,
    queue = loadingTest.queue,
    progress = loadingTest.progress,
    createdAt = loadingTest.createdAt
)

private fun mapErrorTest(errorTest: ErrorTestFragment) = TestResponse.OnErrorTestResponse(
    id = errorTest.id,
    type = errorTest.type,
    createdAt = errorTest.createdAt
)



fun GetTestsQuery.GetTestsResponse.toTestResponses(): GetTestsResponse {
    return GetTestsResponse(
        code = code,
        count = count,
        pages = pages,
        next = next,
        prev = prev,
        tests = tests.mapNotNull { topic ->
            when {
                topic.testResultFragment.readyTestFragment != null -> mapReadyTest(topic.testResultFragment.readyTestFragment)
                topic.testResultFragment.loadingTestFragment != null -> mapLoadingTest(topic.testResultFragment.loadingTestFragment)
                topic.testResultFragment.errorTestFragment != null -> mapErrorTest(topic.testResultFragment.errorTestFragment)
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
                topic.testResultFragment.readyTestFragment != null -> mapReadyTest(topic.testResultFragment.readyTestFragment)
                topic.testResultFragment.loadingTestFragment != null -> mapLoadingTest(topic.testResultFragment.loadingTestFragment)
                topic.testResultFragment.errorTestFragment != null -> mapErrorTest(topic.testResultFragment.errorTestFragment)
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
                essay = this.testData.onEssayTest.essay,
                passedTime = this.testData.onEssayTest.passedTime,
            )
        }
        else -> throw IllegalArgumentException("Response does not contain TestData")
    }
}

fun GetTestStatisticsQuery.GetTestStatisticsResponse.toGetTestStatisticsResponse(): GetTestStatisticsResponse {
    return GetTestStatisticsResponse(
        code = this.code,
        testStatistics = this.testStatistics.map {
            mapReadyTest(it.readyTestFragment)
        }
    )
}