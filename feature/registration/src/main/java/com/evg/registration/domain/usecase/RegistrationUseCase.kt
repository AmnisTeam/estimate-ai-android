package com.evg.registration.domain.usecase

import com.evg.registration.domain.model.User
import com.evg.registration.domain.repository.RegistrationRepository

class RegistrationUseCase(
    private val registrationRepository: RegistrationRepository
) {
}