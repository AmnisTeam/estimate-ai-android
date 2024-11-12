package com.evg.login.domain.mapper

import com.evg.api.type.UserLoginDTO
import com.evg.login.domain.model.User

fun User.toUserLoginDTO(): UserLoginDTO {
    return UserLoginDTO(
        email = this.email,
        password = this.password,
    )
}