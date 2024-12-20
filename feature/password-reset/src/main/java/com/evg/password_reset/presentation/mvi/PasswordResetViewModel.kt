package com.evg.password_reset.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.api.domain.utils.ServerResult
import com.evg.password_reset.domain.model.PasswordReset
import com.evg.password_reset.domain.usecase.PasswordResetUseCases
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class PasswordResetViewModel(
    private val passwordResetUseCases: PasswordResetUseCases,
): ContainerHost<PasswordResetState, PasswordResetSideEffect>, ViewModel() {
    override val container = container<PasswordResetState, PasswordResetSideEffect>(PasswordResetState())

    fun passwordReset(passwordReset: PasswordReset) = intent {
        reduce { state.copy(isEmailResetLoading = true) }
        when (val response = passwordResetUseCases.passwordResetUseCase.invoke(passwordReset = passwordReset)) {
            is ServerResult.Success -> {
                postSideEffect(PasswordResetSideEffect.PasswordResetSuccess)
            }
            is ServerResult.Error -> {
                postSideEffect(PasswordResetSideEffect.PasswordResetFail(combinedPasswordResetError = response.error))
            }
        }
        reduce { state.copy(isEmailResetLoading = false) }
    }
}