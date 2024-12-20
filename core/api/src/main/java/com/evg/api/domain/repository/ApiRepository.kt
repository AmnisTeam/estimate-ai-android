package com.evg.api.domain.repository

import com.evg.api.GetTestsQuery
import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.GetTestsResponse
import com.evg.api.type.PasswordResetDTO
import com.evg.api.type.UserLoginDTO
import com.evg.api.type.UserRegistrationDTO

interface ApiRepository {
    suspend fun registrationUser(user: UserRegistrationDTO): ServerResult<Unit, RegistrationError>
    suspend fun loginUser(user: UserLoginDTO): ServerResult<String, LoginError>
    suspend fun passwordReset(passwordReset: PasswordResetDTO): ServerResult<Unit, PasswordResetError>

    suspend fun getAllTestsByPage(page: Int): ServerResult<GetTestsQuery.GetTests, PasswordResetError>
}