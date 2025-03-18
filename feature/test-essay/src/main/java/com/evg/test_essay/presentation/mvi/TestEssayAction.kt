package com.evg.test_essay.presentation.mvi

import com.evg.test_essay.domain.model.EssayTestData

sealed class TestEssayAction {
    data class SendTest(val data: EssayTestData): TestEssayAction()
}