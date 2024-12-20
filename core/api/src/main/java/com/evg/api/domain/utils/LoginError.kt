package com.evg.api.domain.utils

enum class LoginError: Error {
    WRONG_EMAIL_OR_PASS,
}

sealed class CombinedLoginError : Error {
    data class Network(val networkError: NetworkError) : CombinedLoginError()
    data class Login(val loginError: LoginError) : CombinedLoginError()
}