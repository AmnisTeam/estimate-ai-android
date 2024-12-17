package com.evg.login.domain.usecase

import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.ServerResult
import com.evg.login.domain.model.User
import com.evg.login.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun invoke(user: User): ServerResult<String, LoginError> {
        return loginRepository.loginUser(user = user)
    }
}