package com.evg.test_essay.presentation.mvi

import com.evg.api.domain.utils.NetworkError

sealed class TestEssaySideEffect {
    data object TestEssaySuccess : TestEssaySideEffect()
    data class TestEssayFail(val error: NetworkError) : TestEssaySideEffect()
}