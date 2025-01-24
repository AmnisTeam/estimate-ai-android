package com.evg.statistics.presentation.mapper

import android.content.Context
import com.evg.resource.R
import com.evg.statistics.domain.model.TestStatistics
import com.evg.statistics.presentation.model.StatisticsUI
import com.evg.statistics.presentation.model.TestStatisticsUI
import com.evg.utils.extensions.toDateString
import com.evg.utils.mapper.toTestIcons
import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestScore
import java.time.DayOfWeek
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

fun List<TestStatisticsUI>.reduceSize(): List<TestStatisticsUI> {
    val reduceSize = 30
    if (this.isEmpty()) {
        return emptyList()
    } else if (this.size <= reduceSize) { //TODO sort on server?
        return this.sortedBy { it.createdAt }
    }

    val sortedList = this.sortedBy { it.createdAt }
    val groupSize = (sortedList.size / 30).coerceAtLeast(1)

    sortedList.forEach {
        println(it.createdAt.toDateString())
    }

    return sortedList.chunked(groupSize).mapIndexed { index, group ->
        val avgScore = group.map { it.testScore.score }.average().toInt()
        //val avgCreatedAt = group.map { it.createdAt }.average().toLong()
        val mostCommonType = group.groupingBy { it.type }.eachCount().maxByOrNull { it.value }?.key ?: TestIcons.UNKNOWN

        println(group.first().createdAt.toDateString())
        TestStatisticsUI(
            type = mostCommonType,
            testScore = TestScore(avgScore),
            createdAt = group.first().createdAt,
        )
    }
}

fun DayOfWeek.toStringLocale(context: Context): String {
    return when (this) {
        DayOfWeek.MONDAY -> context.getString(R.string.monday)
        DayOfWeek.TUESDAY -> context.getString(R.string.tuesday)
        DayOfWeek.WEDNESDAY -> context.getString(R.string.wednesday)
        DayOfWeek.THURSDAY -> context.getString(R.string.thursday)
        DayOfWeek.FRIDAY -> context.getString(R.string.friday)
        DayOfWeek.SATURDAY -> context.getString(R.string.saturday)
        DayOfWeek.SUNDAY -> context.getString(R.string.sunday)
    }
}
