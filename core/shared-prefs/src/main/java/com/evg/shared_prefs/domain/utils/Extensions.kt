package com.evg.shared_prefs.domain.utils

import android.content.SharedPreferences

inline fun <reified T : Enum<T>> SharedPreferences.getEnum(key: String, default: T): T {
    val name = getString(key, null) ?: return default
    return try {
        enumValueOf<T>(name)
    } catch (e: Exception) {
        default
    }
}

inline fun <reified T : Enum<T>> SharedPreferences.putEnum(key: String, value: T) {
    edit().putString(key, value.name).apply()
}
