package com.evg.password_reset.presentation.mvi

import com.evg.api.domain.utils.CombinedPasswordResetError

sealed class PasswordResetSideEffect {
    data object PasswordResetSuccess : PasswordResetSideEffect()
    data class PasswordResetFail(val combinedPasswordResetError: CombinedPasswordResetError) : PasswordResetSideEffect()
}