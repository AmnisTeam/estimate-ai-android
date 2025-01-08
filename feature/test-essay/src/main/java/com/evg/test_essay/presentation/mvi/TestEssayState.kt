package com.evg.test_essay.presentation.mvi

import com.evg.test_essay.domain.model.EssayTestData
import kotlinx.coroutines.flow.MutableStateFlow

data class TestEssayState(
    val isTestSending: Boolean = false,
    val isTestDataLoading: Boolean = false,
    val testData: MutableStateFlow<EssayTestData?> = MutableStateFlow(null),
)