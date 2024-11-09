package com.evg.registration.domain.usecase

import com.evg.registration.domain.model.User
import com.evg.registration.domain.repository.RegistrationRepository
import io.reactivex.rxjava3.core.Single

class RegistrationUseCase(
    private val registrationRepository: RegistrationRepository
) {
}