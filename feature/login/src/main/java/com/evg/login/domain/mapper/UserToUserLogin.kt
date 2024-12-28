package com.evg.login.domain.mapper

import com.evg.api.type.UserDTO
import com.evg.login.domain.model.User

fun User.toUserDTO(): UserDTO {
    return UserDTO(
        email = this.email,
        password = this.password,
    )
}