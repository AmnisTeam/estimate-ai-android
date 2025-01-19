package com.evg.statistics.presentation.model

import com.evg.model.TestLevelColors

data class Statistic(
    val level: Int,
    val levelColor: TestLevelColors,
    val timestamp: Long,
)
