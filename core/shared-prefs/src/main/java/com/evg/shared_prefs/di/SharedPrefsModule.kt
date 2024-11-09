package com.evg.shared_prefs.di

import com.evg.shared_prefs.data.repository.SharedPrefsRepositoryImpl
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository
import org.koin.dsl.module

val sharedPrefsModule = module {
    single<SharedPrefsRepository> { SharedPrefsRepositoryImpl(context = get()) }
}