package com.evg.login.domain.usecase

class LoginUseCases(
    val loginUseCase: LoginUseCase,
    val saveUserTokenUseCase: SaveUserTokenUseCase,
    val saveUserUseCase: SaveUserUseCase,
)