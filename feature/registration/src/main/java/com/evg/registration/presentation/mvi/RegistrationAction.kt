package com.evg.registration.presentation.mvi

import com.evg.registration.domain.model.User


sealed class RegistrationAction {
    data class RegistrationUser(val user: User): RegistrationAction()
}