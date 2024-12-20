package com.evg.api.domain.utils

enum class RegistrationError: Error {
    EMAIL_EXIST,
}

sealed class CombinedRegistrationError : Error {
    data class Network(val networkError: NetworkError) : CombinedRegistrationError()
    data class Registration(val registrationError: RegistrationError) : CombinedRegistrationError()
}