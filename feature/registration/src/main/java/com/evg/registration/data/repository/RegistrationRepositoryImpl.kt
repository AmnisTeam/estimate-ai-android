package com.evg.registration.data.repository

import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.registration.domain.mapper.toUserRegistration
import com.evg.registration.domain.model.User
import com.evg.registration.domain.repository.RegistrationRepository

class RegistrationRepositoryImpl(
    private val apiRepository: ApiRepository,
): RegistrationRepository {
    override suspend fun registrationUser(user: User): ServerResult<Unit, RegistrationError> {
        return apiRepository.registrationUser(user = user.toUserRegistration())
    }
}