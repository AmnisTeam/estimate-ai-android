package com.evg.statistics.presentation.model


data class DateTile(
    val dateResId: Int,
    val date: Dates,
) {
    enum class Dates {
        WEEK {
            override fun toTimeRange(): Pair<Long, Long> {
                return calculateTimeRange(days = 7)
            }
        },
        MONTH {
            override fun toTimeRange(): Pair<Long, Long> {
                return calculateTimeRange(days = 30)
            }
        },
        YEAR {
            override fun toTimeRange(): Pair<Long, Long> {
                return calculateTimeRange(days = 365)
            }
        };

        abstract fun toTimeRange(): Pair<Long, Long>

        protected fun calculateTimeRange(days: Int): Pair<Long, Long> {
            val currentMillis = System.currentTimeMillis()
            val oneDayMillis = 86_400_000L // 24 * 60 * 60 * 1000L
            val startDate = currentMillis - days * oneDayMillis
            return startDate to currentMillis
        }
    }
}
