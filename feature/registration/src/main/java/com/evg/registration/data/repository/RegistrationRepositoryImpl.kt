package com.evg.registration.data.repository

import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.CombinedRegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.registration.domain.mapper.toUserDTO
import com.evg.registration.domain.model.User
import com.evg.registration.domain.repository.RegistrationRepository

class RegistrationRepositoryImpl(
    private val apiRepository: ApiRepository,
): RegistrationRepository {
    override suspend fun registrationUser(user: User): ServerResult<Unit, CombinedRegistrationError> {
        return apiRepository.registrationUser(user = user.toUserDTO())
    }
}