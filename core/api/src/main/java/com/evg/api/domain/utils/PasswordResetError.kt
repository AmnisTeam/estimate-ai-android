package com.evg.api.domain.utils

enum class PasswordResetError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN,
}