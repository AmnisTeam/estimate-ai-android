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

    // Include `orbit-viewmodel` for the factory function
    override val container = container<RegistrationState, RegistrationSideEffect>(RegistrationState())

    fun registrationUser(user: User/*, registrationCallback: (String) -> Unit*/) = intent {
        val test = registrationUseCases.registrationUseCase.invoke(user = user)
        when (test) {
            is ServerResult.Success -> {
                postSideEffect(RegistrationSideEffect.RegistrationStatus("Registration... $user"))
            }
            is ServerResult.Error -> {
                postSideEffect(RegistrationSideEffect.RegistrationStatus("Registration error"))
            }
        }
        /*reduce {
            state.copy(total = state.total)
        }*/
    }
}