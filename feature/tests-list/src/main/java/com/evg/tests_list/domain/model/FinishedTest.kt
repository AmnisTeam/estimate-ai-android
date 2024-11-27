package com.evg.tests_list.domain.model

import com.evg.model.TestIcons
import com.evg.model.TestLevelColors

data class FinishedTest(
    val icon: TestIcons,
    val title: String,
    val description: String,
    val level: String,
    val levelColor: TestLevelColors,
)
