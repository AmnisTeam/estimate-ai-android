package com.evg.api.domain.repository

import com.evg.api.domain.model.GetTestDataResponse
import com.evg.api.domain.model.GetTestsResponse
import com.evg.api.domain.model.OnTestProgressResponse
import com.evg.api.domain.utils.CombinedLoginError
import com.evg.api.domain.utils.CombinedPasswordResetError
import com.evg.api.domain.utils.CombinedRegistrationError
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.CreateEssayTestDTO
import com.evg.api.type.PasswordResetDTO
import com.evg.api.type.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ApiRepository {
    suspend fun getAllTestsByPage(page: Int): ServerResult<GetTestsResponse, NetworkError>
    suspend fun getTestDataResponse(id: Int): ServerResult<GetTestDataResponse, NetworkError>

    suspend fun registrationUser(user: UserDTO): ServerResult<Unit, CombinedRegistrationError>
    suspend fun loginUser(user: UserDTO): ServerResult<String, CombinedLoginError>
    suspend fun passwordReset(passwordReset: PasswordResetDTO): ServerResult<Unit, CombinedPasswordResetError>
    suspend fun createEssayTest(data: CreateEssayTestDTO): ServerResult<Unit, NetworkError>

    suspend fun onTestProgress(): SharedFlow<OnTestProgressResponse>

    fun isInternetAvailable(): Boolean
}