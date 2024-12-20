package com.evg.registration.domain.usecase

import com.evg.api.domain.utils.CombinedRegistrationError
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.registration.domain.model.User
import com.evg.registration.domain.repository.RegistrationRepository

class RegistrationUseCase(
    private val registrationRepository: RegistrationRepository
) {
    suspend fun invoke(user: User): ServerResult<Unit, CombinedRegistrationError> {
        return registrationRepository.registrationUser(user = user)
    }
}