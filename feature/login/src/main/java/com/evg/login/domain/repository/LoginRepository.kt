package com.evg.login.domain.repository

import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.ServerResult
import com.evg.login.domain.model.User

interface LoginRepository {
    suspend fun loginUser(user: User): ServerResult<String, LoginError>
}