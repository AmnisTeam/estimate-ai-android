package com.evg.estimateai

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.ActivityCompat
import com.evg.api.di.apiModule
import com.evg.database.di.databaseModule
import com.evg.login.di.loginModule
import com.evg.password_reset.di.passwordResetModule
import com.evg.registration.di.registrationModule
import com.evg.shared_prefs.di.sharedPrefsModule
import com.evg.statistics.di.statisticsModule
import com.evg.test_essay.di.testEssayModule
import com.evg.tests_list.di.testsListModule
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
            modules(sharedPrefsModule, apiModule, registrationModule, databaseModule, loginModule, passwordResetModule, testsListModule, testEssayModule, statisticsModule)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val loadingChannel = NotificationChannel(
                "loading_status_tests",
                "Loading the status of tests",
                NotificationManager.IMPORTANCE_LOW,
            )
            val readyChannel = NotificationChannel(
                "ready_status_tests",
                "Ready the status of tests",
                NotificationManager.IMPORTANCE_MIN,
            )
            val errorChannel = NotificationChannel(
                "error_status_tests",
                "Error the status of tests",
                NotificationManager.IMPORTANCE_MIN,
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannels(listOf(loadingChannel, readyChannel, errorChannel))
        }
    }
}