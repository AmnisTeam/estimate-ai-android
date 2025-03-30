package com.evg.login.domain.usecase

import com.evg.shared_prefs.domain.repository.SharedPrefsRepository

class SaveUserUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository,
) {
    fun invoke(email: String) {
        return sharedPrefsRepository.saveUser(email = email)
    }
}