package com.evg.registration.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.registration.domain.model.User
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class RegistrationViewModel: ContainerHost<RegistrationState, RegistrationSideEffect>, ViewModel() {

    // Include `orbit-viewmodel` for the factory function
    override val container = container<RegistrationState, RegistrationSideEffect>(RegistrationState())

    fun registrationUser(user: User, registrationCallback: (String) -> Unit) = intent {
        postSideEffect(RegistrationSideEffect.RegistrationStatus("Registration... $user"))

        /*reduce {
            state.copy(total = state.total)
        }*/
    }
}