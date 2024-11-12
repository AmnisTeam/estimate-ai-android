package com.evg.password_reset.domain.mapper

import com.evg.api.type.PasswordResetDTO
import com.evg.password_reset.domain.model.PasswordReset

fun PasswordReset.toPasswordReset(): PasswordResetDTO {
    return PasswordResetDTO(
        email = this.email,
    )
}