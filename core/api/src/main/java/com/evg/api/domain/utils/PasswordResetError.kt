package com.evg.api.domain.utils

enum class PasswordResetError: Error {
    UNKNOWN_EMAIL,
}

sealed class CombinedPasswordResetError : Error {
    data class Network(val networkError: NetworkError) : CombinedPasswordResetError()
    data class PasswordReset(val passwordResetError: PasswordResetError) : CombinedPasswordResetError()
}