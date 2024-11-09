package com.evg.estimateai

import android.app.Application
import com.evg.api.di.apiModule
import com.evg.registration.di.registrationModule
import com.evg.shared_prefs.di.sharedPrefsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG) //TODO
            androidContext(this@App)
            modules(sharedPrefsModule, registrationModule, apiModule)
        }
    }
}