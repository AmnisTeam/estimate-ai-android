package com.evg.statistics.presentation.model

import com.evg.utils.model.TestScore
import java.time.DayOfWeek

data class StatisticsUI(
    val frequentLevel: TestScore?,
    val frequentDayOfWeek: DayOfWeek?,
    val testStatisticsUI: List<TestStatisticsUI>,
)