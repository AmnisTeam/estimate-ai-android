package com.evg.tests_list.domain.model

import com.evg.model.TestIcons

data class LoadingTest(
    val icon: TestIcons,
    val title: String,
    val description: String,
    val progress: Int,
)
