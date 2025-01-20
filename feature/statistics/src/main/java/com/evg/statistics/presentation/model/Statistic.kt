package com.evg.statistics.presentation.model

import com.evg.utils.model.TestLevelColors

data class Statistic(
    val level: Int,
    val levelColor: TestLevelColors,
    val timestamp: Long,
)
