package com.evg.login.domain.mapper

import com.evg.api.type.UserLogin
import com.evg.login.domain.model.User

fun User.toUserLogin(): UserLogin {
    return UserLogin(
        email = this.email,
        password = this.password,
    )
}