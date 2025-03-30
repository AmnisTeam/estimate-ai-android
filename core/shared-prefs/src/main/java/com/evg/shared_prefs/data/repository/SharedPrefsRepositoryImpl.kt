package com.evg.shared_prefs.data.repository

import android.content.Context
import com.evg.shared_prefs.domain.model.SharedPrefsAppLanguage
import com.evg.shared_prefs.domain.model.SharedPrefsAppStyle
import com.evg.shared_prefs.domain.model.SharedPrefsAppTheme
import com.evg.shared_prefs.domain.model.SharedPrefsTestingLanguage
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository
import com.evg.shared_prefs.domain.utils.getEnum
import com.evg.shared_prefs.domain.utils.putEnum

class SharedPrefsRepositoryImpl(
    context: Context,
) : SharedPrefsRepository {
    companion object {
        private const val PREFS_NAME = "myPreferences"
        private const val KEY_USER_TOKEN = "userToken"
        private const val KEY_USER_EMAIL = "userEmail"
        private const val KEY_APP_LANGUAGE = "app_language"
        private const val KEY_APP_THEME = "app_theme"
        private const val KEY_TESTING_LANGUAGE = "testing_language"
        private const val KEY_APP_STYLE = "app_style"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveUserToken(token: String) {
        with(prefs.edit()) {
            putString(KEY_USER_TOKEN, token)
            apply()
        }
    }

    override fun getUserToken(): String? {
        return prefs.getString(KEY_USER_TOKEN, null)
    }


    override fun saveUser(email: String) {
        with(prefs.edit()) {
            putString(KEY_USER_EMAIL, email)
            apply()
        }
    }

    override fun getUser(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }


    override fun resetUser() {
        with(prefs.edit()) {
            putString(KEY_USER_TOKEN, null)
            putString(KEY_USER_EMAIL, null)
            apply()
        }
    }


    override fun saveAppLanguage(language: SharedPrefsAppLanguage) {
        prefs.putEnum(KEY_APP_LANGUAGE, language)
    }

    override fun getAppLanguage(): SharedPrefsAppLanguage {
        return prefs.getEnum(KEY_APP_LANGUAGE, SharedPrefsAppLanguage.USER)
    }


    override fun saveAppTheme(theme: SharedPrefsAppTheme) {
        prefs.putEnum(KEY_APP_THEME, theme)
    }

    override fun getAppTheme(): SharedPrefsAppTheme {
        return prefs.getEnum(KEY_APP_THEME, SharedPrefsAppTheme.USER)
    }


    override fun saveTestingLanguage(language: SharedPrefsTestingLanguage) {
        prefs.putEnum(KEY_TESTING_LANGUAGE, language)
    }

    override fun getTestingLanguage(): SharedPrefsTestingLanguage {
        return prefs.getEnum(KEY_TESTING_LANGUAGE, SharedPrefsTestingLanguage.ENGLISH)
    }


    override fun saveAppStyle(style: SharedPrefsAppStyle) {
        prefs.putEnum(KEY_APP_STYLE, style)
    }

    override fun getAppStyle(): SharedPrefsAppStyle {
        return prefs.getEnum(KEY_APP_STYLE, SharedPrefsAppStyle.PURPLE)
    }
}
