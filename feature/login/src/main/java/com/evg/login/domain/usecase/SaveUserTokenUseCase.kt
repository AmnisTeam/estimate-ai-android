package com.evg.login.domain.usecase

import com.evg.shared_prefs.domain.repository.SharedPrefsRepository

class SaveUserTokenUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository,
) {
    fun invoke(token: String) {
        return sharedPrefsRepository.saveUserToken(token = token)
    }
}