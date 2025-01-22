package com.evg.statistics.presentation.model

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope


data class DateTile(
    val dateResId: Int,
    val date: DateRange
)

sealed class DateRange {
    abstract fun toTimeRange(): Pair<Long, Long>

    data object Week : DateRange() {
        override fun toTimeRange(): Pair<Long, Long> = calculateTimeRange(days = 7)
    }

    data object Month : DateRange() {
        override fun toTimeRange(): Pair<Long, Long> = calculateTimeRange(days = 30)
    }

    data object Year : DateRange() {
        override fun toTimeRange(): Pair<Long, Long> = calculateTimeRange(days = 365)
    }

    class Custom(private val timeRange: Pair<Long, Long>) : DateRange() {
        override fun toTimeRange(): Pair<Long, Long> = timeRange
    }

    protected fun calculateTimeRange(days: Int): Pair<Long, Long> {
        val currentMillis = System.currentTimeMillis()
        val oneDayMillis = 86_400_000L // 24 * 60 * 60 * 1000L
        val startDate = currentMillis - days * oneDayMillis
        return startDate to currentMillis
    }
}


val dateRangeSaver: Saver<DateRange, Any> = object : Saver<DateRange, Any> {
    override fun restore(value: Any): DateRange? {
        return when (value) {
            "Week" -> DateRange.Week
            "Month" -> DateRange.Month
            "Year" -> DateRange.Year
            is Pair<*, *> -> DateRange.Custom(Pair(value.first as Long, value.second as Long))
            else -> null
        }
    }

    override fun SaverScope.save(value: DateRange): Any {
        return when (value) {
            is DateRange.Week -> "Week"
            is DateRange.Month -> "Month"
            is DateRange.Year -> "Year"
            is DateRange.Custom -> value.toTimeRange()
        }
    }
}
