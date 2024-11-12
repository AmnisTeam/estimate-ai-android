package com.evg.password_reset.data.repository

import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.PasswordResetError
import com.evg.api.domain.utils.ServerResult
import com.evg.password_reset.domain.mapper.toPasswordReset
import com.evg.password_reset.domain.model.PasswordReset
import com.evg.password_reset.domain.repository.PasswordResetRepository

class PasswordResetRepositoryImpl(
    private val apiRepository: ApiRepository,
): PasswordResetRepository {
    override suspend fun passwordReset(passwordReset: PasswordReset): ServerResult<Unit, PasswordResetError> {
        return apiRepository.passwordReset(passwordReset = passwordReset.toPasswordReset())
    }
}