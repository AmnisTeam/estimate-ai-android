package com.evg.login.presentation.mvi

import com.evg.login.domain.model.User

sealed class LoginAction {
    data class LoginUser(val user: User): LoginAction()
}