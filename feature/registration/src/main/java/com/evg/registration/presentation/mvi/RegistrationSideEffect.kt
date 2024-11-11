package com.evg.registration.presentation.mvi

import com.evg.api.domain.utils.RegistrationError

sealed class RegistrationSideEffect {
    data object RegistrationSuccess : RegistrationSideEffect()
    data class RegistrationFail(val error: RegistrationError) : RegistrationSideEffect()
}