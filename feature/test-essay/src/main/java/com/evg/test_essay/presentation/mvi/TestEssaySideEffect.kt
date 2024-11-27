package com.evg.test_essay.presentation.mvi

import com.evg.api.domain.utils.RegistrationError

sealed class TestEssaySideEffect {
    data object TestEssaySuccess : TestEssaySideEffect()
    data class TestEssayFail(val error: RegistrationError) : TestEssaySideEffect()
}