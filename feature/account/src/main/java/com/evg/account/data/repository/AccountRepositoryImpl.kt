package com.evg.account.data.repository

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import com.evg.account.domain.mapper.toAccountAppLanguage
import com.evg.account.domain.mapper.toAppStyle
import com.evg.account.domain.mapper.toAppTheme
import com.evg.account.domain.mapper.toSharedPrefsAppLanguage
import com.evg.account.domain.mapper.toSharedPrefsAppStyle
import com.evg.account.domain.mapper.toSharedPrefsAppTheme
import com.evg.account.domain.mapper.toSharedPrefsTestingLanguage
import com.evg.account.domain.mapper.toTestingLanguage
import com.evg.account.domain.model.AppLanguage
import com.evg.account.domain.model.AppStyle
import com.evg.account.domain.model.AppTheme
import com.evg.account.domain.model.TestingLanguage
import com.evg.account.domain.repository.AccountRepository
import com.evg.api.domain.repository.ApiRepository
import com.evg.database.domain.repository.DatabaseRepository
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository

class AccountRepositoryImpl(
    private val context: Context,
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val apiRepository: ApiRepository,
    private val databaseRepository: DatabaseRepository,
): AccountRepository {
    override fun getUser(): String? {
        return sharedPrefsRepository.getUser()?.substringBefore('@')
    }

    override suspend fun logout() {
        // Close socket
        apiRepository.closeSocket()

        // Stop service
        val intent = Intent("STOP_STATUS_SERVICE").apply {
            setPackage(context.packageName)
        }
        context.sendBroadcast(intent)

        // Delete notifications
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancelAll()

        // Delete user
        sharedPrefsRepository.resetUser()

        // Delete database
        databaseRepository.clearDatabase()
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

    override fun saveAppStyle(style: AppStyle) {
        sharedPrefsRepository.saveAppStyle(style = style.toSharedPrefsAppStyle())
    }
    override fun getAppStyle(): AppStyle {
        return sharedPrefsRepository.getAppStyle().toAppStyle()
    }
}