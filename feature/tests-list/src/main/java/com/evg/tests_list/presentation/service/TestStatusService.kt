package com.evg.tests_list.presentation.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.evg.api.domain.utils.ServerResult
import com.evg.resource.R
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.usecase.ConnectTestProgressUseCase
import com.evg.tests_list.domain.usecase.TestsListUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.serviceScope

class TestStatusService : Service() {
    companion object {
        private const val SERVICE_ID = 1
    }
    private var isRunning = false

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.START.toString() -> {
                start()
            }
            Actions.STOP.toString() -> {
                stop()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        if (isRunning) return
        isRunning = true

        val notification = NotificationCompat.Builder(this, "updating_status_tests")
            .setSmallIcon(R.drawable.discord)
            .setContentTitle("Title")
            .setContentText("Text")
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            startForeground(SERVICE_ID, notification)
        } else {
            startForeground(SERVICE_ID, notification, FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }
    }

    private fun stop() {
        isRunning = false
        stopSelf()
    }

    enum class Actions {
        START, STOP,
    }
}