package com.evg.statistics.domain.mapper

import com.evg.api.domain.model.GetTestStatisticsResponse
import com.evg.database.domain.model.TestTypeDBO
import com.evg.statistics.domain.model.TestStatistics

fun GetTestStatisticsResponse.toTestStatistics(): List<TestStatistics> {
    return this.testStatistics.map {
        TestStatistics(
            id = it.id,
            type = it.type,
            score = it.score,
            createdAt = it.createdAt,
        )
    }
}

fun List<TestTypeDBO>.toTestStatistics(): List<TestStatistics> {
    return this.mapNotNull { test ->
        test.readyTestTypeDBO?.let { readyTest ->
            TestStatistics(
                id = test.id,
                type = readyTest.type,
                score = readyTest.score,
                createdAt = readyTest.createdAt,
            )
        }
    }
}