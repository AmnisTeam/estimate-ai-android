package com.evg.statistics.presentation.model

import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestScore

data class TestStatisticsUI(
    val type: TestIcons,
    val testScore: TestScore,
    val createdAt: Long,
)
