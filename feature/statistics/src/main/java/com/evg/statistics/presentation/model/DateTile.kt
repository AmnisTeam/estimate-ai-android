package com.evg.statistics.presentation.model


data class DateTile(
    val dateResId: Int,
    val date: Dates,
) {
    enum class Dates {
        WEEK,
        MONTH,
        YEAR,
    }
}
