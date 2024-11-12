package com.evg.password_reset.presentation.mvi

import com.evg.api.domain.utils.PasswordResetError

sealed class PasswordResetSideEffect {
    data object PasswordResetSuccess : PasswordResetSideEffect()
    data class PasswordResetFail(val error: PasswordResetError) : PasswordResetSideEffect()
}