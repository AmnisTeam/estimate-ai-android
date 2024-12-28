package com.evg.registration.domain.mapper

import com.evg.api.type.UserDTO
import com.evg.registration.domain.model.User

fun User.toUserDTO(): UserDTO {
    return UserDTO(
        email = this.email,
        password = this.password,
    )
}