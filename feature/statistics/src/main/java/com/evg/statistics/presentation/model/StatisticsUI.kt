package com.evg.statistics.presentation.model

import com.evg.utils.model.TestLevelColors
import java.time.DayOfWeek

data class StatisticsUI(
    val frequentLevel: TestLevelColors?,
    val frequentDayOfWeek: DayOfWeek?,
    val testStatisticsUI: List<TestStatisticsUI>,
)