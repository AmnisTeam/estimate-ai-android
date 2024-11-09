package com.evg.api.domain.repository

import com.evg.api.RegisterUserMutation
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.UserRegistration
import com.evg.api.type.UserRegistrationResponse

interface ApiRepository {
    suspend fun registrationUser(user: UserRegistration): ServerResult<RegisterUserMutation.RegisterUser, RegistrationError>
}