package com.evg.api.domain.repository

import com.evg.api.GetTestsQuery
import com.evg.api.domain.utils.CombinedLoginError
import com.evg.api.domain.utils.CombinedPasswordResetError
import com.evg.api.domain.utils.CombinedRegistrationError
import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.GetTestsResponse
import com.evg.api.type.PasswordResetDTO
import com.evg.api.type.UserLoginDTO
import com.evg.api.type.UserRegistrationDTO

interface ApiRepository {
    suspend fun registrationUser(user: UserRegistrationDTO): ServerResult<Unit, CombinedRegistrationError>
    suspend fun loginUser(user: UserLoginDTO): ServerResult<String, CombinedLoginError>
    suspend fun passwordReset(passwordReset: PasswordResetDTO): ServerResult<Unit, CombinedPasswordResetError>

    suspend fun getAllTestsByPage(page: Int): ServerResult<GetTestsQuery.GetTests, NetworkError>
}