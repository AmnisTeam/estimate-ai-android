package com.evg.api.domain.repository

import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.UserLogin
import com.evg.api.type.UserRegistration

interface ApiRepository {
    suspend fun registrationUser(user: UserRegistration): ServerResult<Unit, RegistrationError>
    suspend fun loginUser(user: UserLogin): ServerResult<Unit, LoginError>
}