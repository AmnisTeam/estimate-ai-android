package com.evg.registration.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.api.domain.utils.ServerResult
import com.evg.registration.domain.model.User
import com.evg.registration.domain.usecase.RegistrationUseCases
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class RegistrationViewModel(
    private val registrationUseCases: RegistrationUseCases,
): ContainerHost<RegistrationState, RegistrationSideEffect>, ViewModel() {
    override val container = container<RegistrationState, RegistrationSideEffect>(RegistrationState())

    fun registrationUser(user: User) = intent {
        reduce { state.copy(isRegistrationLoading = true) }
        when (val response = registrationUseCases.registrationUseCase.invoke(user = user)) {
            is ServerResult.Success -> {
                postSideEffect(RegistrationSideEffect.RegistrationSuccess)
            }
            is ServerResult.Error -> {
                postSideEffect(RegistrationSideEffect.RegistrationFail(combinedRegistrationError = response.error))
            }
        }
        reduce { state.copy(isRegistrationLoading = false) }
    }
}