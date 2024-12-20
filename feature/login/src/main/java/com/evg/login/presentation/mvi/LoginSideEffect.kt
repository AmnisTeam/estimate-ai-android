package com.evg.login.presentation.mvi

import com.evg.api.domain.utils.CombinedLoginError

sealed class LoginSideEffect {
    data object LoginSuccess : LoginSideEffect()
    data class LoginFail(val combinedLoginError: CombinedLoginError) : LoginSideEffect()
}