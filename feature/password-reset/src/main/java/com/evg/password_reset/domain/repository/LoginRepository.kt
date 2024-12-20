package com.evg.password_reset.domain.repository

import com.evg.api.domain.utils.CombinedPasswordResetError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.api.domain.utils.ServerResult
import com.evg.password_reset.domain.model.PasswordReset

interface PasswordResetRepository {
    suspend fun passwordReset(passwordReset: PasswordReset): ServerResult<Unit, CombinedPasswordResetError>
}