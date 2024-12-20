package com.evg.registration.presentation.mvi

import com.evg.api.domain.utils.CombinedRegistrationError

sealed class RegistrationSideEffect {
    data object RegistrationSuccess : RegistrationSideEffect()
    data class RegistrationFail(val combinedRegistrationError: CombinedRegistrationError) : RegistrationSideEffect()
}