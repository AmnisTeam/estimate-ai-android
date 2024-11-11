package com.evg.login.presentation.mvi

import com.evg.api.domain.utils.LoginError

sealed class LoginSideEffect {
    data object LoginSuccess : LoginSideEffect()
    data class LoginFail(val error: LoginError) : LoginSideEffect()
}