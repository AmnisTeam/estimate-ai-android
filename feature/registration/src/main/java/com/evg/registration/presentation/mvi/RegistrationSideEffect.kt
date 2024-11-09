package com.evg.registration.presentation.mvi

sealed class RegistrationSideEffect {
    data class RegistrationStatus(val text: String) : RegistrationSideEffect()
}