package com.evg.account.data.repository

import com.evg.account.domain.mapper.toAccountAppLanguage
import com.evg.account.domain.mapper.toAppTheme
import com.evg.account.domain.mapper.toSharedPrefsAppLanguage
import com.evg.account.domain.mapper.toSharedPrefsAppTheme
import com.evg.account.domain.mapper.toSharedPrefsTestingLanguage
import com.evg.account.domain.mapper.toTestingLanguage
import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.AppTheme
import com.evg.account.domain.model.TestingLanguage
import com.evg.account.domain.repository.AccountRepository
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository

class AccountRepositoryImpl(
    private val sharedPrefsRepository: SharedPrefsRepository,
): AccountRepository {
    override fun logout() {
        sharedPrefsRepository.resetUserToken()
    }

    override fun saveAppLanguage(language: AppLanguage) {
        sharedPrefsRepository.saveAppLanguage(language = language.toSharedPrefsAppLanguage())
    }
    override fun getAppLanguage(): AppLanguage {
        return sharedPrefsRepository.getAppLanguage().toAccountAppLanguage()
    }

    override fun saveAppTheme(theme: AppTheme) {
        sharedPrefsRepository.saveAppTheme(theme = theme.toSharedPrefsAppTheme())
    }
    override fun getAppTheme(): AppTheme {
        return sharedPrefsRepository.getAppTheme().toAppTheme()
    }

    override fun saveTestingLanguage(language: TestingLanguage) {
        sharedPrefsRepository.saveTestingLanguage(language = language.toSharedPrefsTestingLanguage())
    }
    override fun getTestingLanguage(): TestingLanguage {
        return sharedPrefsRepository.getTestingLanguage().toTestingLanguage()
    }
}