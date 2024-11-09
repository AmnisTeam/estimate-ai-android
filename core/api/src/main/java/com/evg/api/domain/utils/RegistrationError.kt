package com.evg.api.domain.utils

enum class RegistrationError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    SERVER_ERROR,
    SERIALIZATION,
    EMAIL_EXIST,
    PASSWORD_MISMATCH,
    UNKNOWN,
}