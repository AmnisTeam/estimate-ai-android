package com.evg.tests_list.presentation.mvi

import com.evg.api.domain.utils.RegistrationError

sealed class TestsListSideEffect {
    data object TestsListSuccess : TestsListSideEffect()
    data class TestsListFail(val error: RegistrationError) : TestsListSideEffect()
}