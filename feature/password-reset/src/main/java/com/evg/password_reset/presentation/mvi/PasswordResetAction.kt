package com.evg.password_reset.presentation.mvi

import com.evg.password_reset.domain.model.PasswordReset

sealed class PasswordResetAction {
    data class PassReset(val passwordReset: PasswordReset): PasswordResetAction()
}