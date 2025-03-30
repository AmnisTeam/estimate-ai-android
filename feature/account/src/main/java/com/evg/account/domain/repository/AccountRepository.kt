package com.evg.account.domain.repository

import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.AppStyle
import com.evg.account.domain.model.AppTheme
import com.evg.account.domain.model.TestingLanguage

interface AccountRepository {
    fun getUser(): String?
    fun logout()

    fun saveAppLanguage(language: AppLanguage)
    fun getAppLanguage(): AppLanguage

    fun saveAppTheme(theme: AppTheme)
    fun getAppTheme(): AppTheme

    fun saveTestingLanguage(language: TestingLanguage)
    fun getTestingLanguage(): TestingLanguage

    fun saveAppStyle(style: AppStyle)
    fun getAppStyle(): AppStyle
}