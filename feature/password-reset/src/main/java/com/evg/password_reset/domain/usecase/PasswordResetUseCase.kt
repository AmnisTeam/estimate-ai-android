package com.evg.password_reset.domain.usecase

import com.evg.api.domain.utils.CombinedPasswordResetError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.api.domain.utils.ServerResult
import com.evg.password_reset.domain.model.PasswordReset
import com.evg.password_reset.domain.repository.PasswordResetRepository

class PasswordResetUseCase(
    private val passwordResetRepository: PasswordResetRepository
) {
    suspend fun invoke(passwordReset: PasswordReset): ServerResult<Unit, CombinedPasswordResetError> {
        return passwordResetRepository.passwordReset(passwordReset = passwordReset)
    }
}