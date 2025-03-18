package com.evg.shared_prefs.domain.repository

import com.evg.shared_prefs.domain.model.SharedPrefsAppLanguage
import com.evg.shared_prefs.domain.model.SharedPrefsAppStyle
import com.evg.shared_prefs.domain.model.SharedPrefsAppTheme
import com.evg.shared_prefs.domain.model.SharedPrefsTestingLanguage

interface SharedPrefsRepository {
    fun saveUserToken(token: String)
    fun getUserToken(): String?
    fun resetUserToken()

    fun saveAppLanguage(language: SharedPrefsAppLanguage)
    fun getAppLanguage(): SharedPrefsAppLanguage

    fun saveAppTheme(theme: SharedPrefsAppTheme)
    fun getAppTheme(): SharedPrefsAppTheme

    fun saveTestingLanguage(language: SharedPrefsTestingLanguage)
    fun getTestingLanguage(): SharedPrefsTestingLanguage

    fun saveAppStyle(style: SharedPrefsAppStyle)
    fun getAppStyle(): SharedPrefsAppStyle
}