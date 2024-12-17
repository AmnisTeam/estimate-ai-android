package com.evg.login.data.repository

import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.ServerResult
import com.evg.login.domain.mapper.toUserLoginDTO
import com.evg.login.domain.model.User
import com.evg.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val apiRepository: ApiRepository,
): LoginRepository {
    override suspend fun loginUser(user: User): ServerResult<String, LoginError> {
        return apiRepository.loginUser(user = user.toUserLoginDTO())
    }
}