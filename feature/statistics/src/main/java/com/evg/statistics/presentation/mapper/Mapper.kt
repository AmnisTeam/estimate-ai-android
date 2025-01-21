package com.evg.statistics.presentation.mapper

import com.evg.statistics.domain.model.TestStatistics
import com.evg.statistics.presentation.model.StatisticsUI
import com.evg.statistics.presentation.model.TestStatisticsUI
import com.evg.utils.mapper.toTestIcons
import com.evg.utils.model.TestScore
import java.time.Instant
import java.time.ZoneId

fun List<TestStatistics>.toStatisticsUI(): StatisticsUI {
    val frequentDayOfWeek = this
        .map { Instant.ofEpochMilli(it.createdAt).atZone(ZoneId.systemDefault()).toLocalDate() }
        .groupingBy { it.dayOfWeek }
        .eachCount()
        .maxByOrNull { it.value }?.key

    val frequentLevel = this
        .groupingBy { it.score }
        .eachCount()
        .maxByOrNull { it.value }
        ?.key
        ?.let { TestScore(it) }

    val testStatisticsUI = this.map { testStatistic ->
        TestStatisticsUI(
            type = testStatistic.type.toTestIcons(),
            testScore = TestScore(testStatistic.score),
            createdAt = testStatistic.createdAt,
        )
    }

    return StatisticsUI(
        frequentLevel = frequentLevel,
        frequentDayOfWeek = frequentDayOfWeek,
        testStatisticsUI = testStatisticsUI
    )
}
