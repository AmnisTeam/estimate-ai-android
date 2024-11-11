package com.evg.login.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.api.domain.utils.ServerResult
import com.evg.login.domain.model.User
import com.evg.login.domain.usecase.LoginUseCases
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class LoginViewModel(
    private val loginUseCases: LoginUseCases,
): ContainerHost<LoginState, LoginSideEffect>, ViewModel() {
    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun loginUser(user: User) = intent {
        reduce { state.copy(isLoginLoading = true) }
        when (val response = loginUseCases.loginUseCase.invoke(user = user)) {
            is ServerResult.Success -> {
                postSideEffect(LoginSideEffect.LoginSuccess)
            }
            is ServerResult.Error -> {
                postSideEffect(LoginSideEffect.LoginFail(error = response.error))
            }
        }
        reduce { state.copy(isLoginLoading = false) }
    }
}