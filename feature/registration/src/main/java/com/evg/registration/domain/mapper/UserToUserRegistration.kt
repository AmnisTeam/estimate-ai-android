package com.evg.registration.domain.mapper

import com.evg.api.type.UserRegistration
import com.evg.registration.domain.model.User

fun User.toUserRegistration(): UserRegistration {
    return UserRegistration(
        email = this.email,
        password = this.password,
    )
}