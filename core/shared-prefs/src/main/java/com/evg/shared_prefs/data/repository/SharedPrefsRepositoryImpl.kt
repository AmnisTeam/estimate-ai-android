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
): SharedPrefsRepository {
    private val prefs = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

    /**
     * Сохраняет токен пользователя [token] в SharedPreferences.
     */
    override fun saveUserToken(token: String) {
        with(prefs.edit()) {
            putString("userToken", token)
            apply()
        }
    }

    /**
     * Возвращает токен пользователя из SharedPreferences или null, если токен не найден.
     */
    override fun getUserToken(): String? {
        val userToken = prefs.getString("userToken", null)
        return userToken
    }

    /**
     * Сбрасывает токен пользователя в SharedPreferences.
     */
    override fun resetUserToken() {
        with(prefs.edit()) {
            putString("userToken", null)
            apply()
        }
    }


    /**
     * Сохраняет текущий язык приложения [language] в SharedPreferences.
     */
    override fun saveAppLanguage(language: SharedPrefsAppLanguage) {
        prefs.putEnum("app_language", language)
    }

    /**
     * Возвращает сохранённый язык приложения из SharedPreferences.
     * Если язык не найден, возвращает значение по умолчанию [SharedPrefsAppLanguage.USER].
     */
    override fun getAppLanguage(): SharedPrefsAppLanguage {
        return prefs.getEnum("app_language", SharedPrefsAppLanguage.USER)
    }


    /**
     * Сохраняет текущую тему приложения [theme] в SharedPreferences.
     */
    override fun saveAppTheme(theme: SharedPrefsAppTheme) {
        prefs.putEnum("app_theme", theme)
    }

    /**
     * Возвращает сохранённую тему приложения из SharedPreferences.
     * Если тема не найдена, возвращает значение по умолчанию [SharedPrefsAppTheme.USER].
     */
    override fun getAppTheme(): SharedPrefsAppTheme {
        return prefs.getEnum("app_theme", SharedPrefsAppTheme.USER)
    }


    /**
     * Сохраняет язык тестирования [language] в SharedPreferences.
     */
    override fun saveTestingLanguage(language: SharedPrefsTestingLanguage) {
        prefs.putEnum("testing_language", language)
    }

    /**
     * Возвращает сохранённый язык тестирования из SharedPreferences.
     * Если язык не найден, возвращает значение по умолчанию [SharedPrefsTestingLanguage.ENGLISH].
     */
    override fun getTestingLanguage(): SharedPrefsTestingLanguage {
        return prefs.getEnum("testing_language", SharedPrefsTestingLanguage.ENGLISH)
    }


    /**
     * Сохраняет текущий стиль приложения [style] в SharedPreferences.
     */
    override fun saveAppStyle(style: SharedPrefsAppStyle) {
        prefs.putEnum("app_style", style)
    }

    /**
     * Возвращает сохранённый стиль приложения из SharedPreferences.
     * Если стиль не найден, возвращает значение по умолчанию [SharedPrefsAppStyle.PURPLE].
     */
    override fun getAppStyle(): SharedPrefsAppStyle {
        return prefs.getEnum("app_style", SharedPrefsAppStyle.PURPLE)
    }
}
