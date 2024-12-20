package com.evg.registration.domain.repository

import com.evg.api.domain.utils.CombinedRegistrationError
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.registration.domain.model.User

interface RegistrationRepository {
    suspend fun registrationUser(user: User): ServerResult<Unit, CombinedRegistrationError>
}