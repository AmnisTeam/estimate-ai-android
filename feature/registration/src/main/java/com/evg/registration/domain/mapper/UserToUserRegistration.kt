package com.evg.registration.domain.mapper

import com.evg.api.type.UserRegistrationDTO
import com.evg.registration.domain.model.User

fun User.toUserRegistration(): UserRegistrationDTO {
    return UserRegistrationDTO(
        email = this.email,
        password = this.password,
    )
}