package com.evg.tests_list.presentation.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import com.evg.api.domain.utils.ServerResult
import com.evg.model.TestIcons
import com.evg.resource.R
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.usecase.ConnectTestProgressUseCase
import com.evg.tests_list.presentation.mapper.toTestState
import com.evg.tests_list.presentation.model.TestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class TestStatusService : Service() {
    companion object {
        private const val LOADING_TEST_ID = Int.MAX_VALUE
        //private const val LOADING_GROUP = "com.evg.tests_list.LOADING_GROUP"
        private const val READY_GROUP = "com.evg.tests_list.READY_GROUP"
        private const val ERROR_GROUP = "com.evg.tests_list.ERROR_GROUP"
    }
    enum class Actions {
        START, STOP,
    }
    private var isStarted = false
    private var job: Job? = null
    private val connectTestProgressUseCase: ConnectTestProgressUseCase by inject()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> startSocketConnection()
            Actions.STOP.toString() -> stopSocketConnection()
        }
        return START_STICKY
    }

    private var cnt = 0
    private fun startSocketConnection() {
        if (job != null) return
        println("startSocketConnection")

        job = CoroutineScope(Dispatchers.IO).launch {
            when (val result = connectTestProgressUseCase.invoke()) {
                is ServerResult.Success -> {
                    result.data.collect { tests ->
                        println("data collected in service №${++cnt}")
                        if (!isStarted) {
                            start(tests = tests.map { it.toTestState() })
                        } else if (tests.isNotEmpty()) {
                            updateNotification(tests = tests.map { it.toTestState() })
                        } else {
                            stopSocketConnection()
                        }
                    }
                }
                is ServerResult.Error -> {
                    stopSocketConnection()
                }
            }
        }
    }

    private fun stopSocketConnection() {
        println("stop Socket Connection")
        isStarted = false
        job?.cancel()
        stopSelf()
    }

    private fun start(tests: List<TestState>) {
        isStarted = true

        val notification = if (tests.isEmpty()) {
            NotificationCompat.Builder(this, "loading_status_tests")
                .setSmallIcon(R.drawable.discord)
                .setOnlyAlertOnce(true)
                .setContentTitle("Loading tests")
                .setProgress(100, 0, true)
                .build()
        } else {
            processTests(tests)
        } ?: return

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            startForeground(LOADING_TEST_ID, notification)
        } else {
            startForeground(LOADING_TEST_ID, notification, FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }
    }

    private fun updateNotification(tests: List<TestState>) {
        val notification = processTests(tests) ?: return

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(LOADING_TEST_ID, notification)
    }

    private fun processTests(tests: List<TestState>): Notification? {
        val onFinishedTests = tests.filterIsInstance<TestState.FinishedTest>()
        val onErrorTests = tests.filterIsInstance<TestState.ErrorTest>()
        val onLoadingTests = tests.filterIsInstance<TestState.LoadingTest>()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (onFinishedTests.isNotEmpty()) {
            val readyTestGroup = NotificationCompat.Builder(this, "ready_status_tests")
                .setSmallIcon(R.drawable.discord)
                .setGroup(READY_GROUP)
                .setGroupSummary(true)
                .build()
            notificationManager.notify(-1, readyTestGroup);

            onFinishedTests.forEach { test ->
                val testType = when (test.icon) {
                    TestIcons.ESSAY -> "test-essay"
                    TestIcons.UNKNOWN -> null
                }
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("app://${testType}/${test.id}")
                }
                val pendingIntent = PendingIntent.getActivity(
                    this,
                    test.id,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val notification = NotificationCompat.Builder(this, "ready_status_tests")
                    .setSmallIcon(R.drawable.discord)
                    .setContentTitle("Test №${test.id} is ready!")
                    .setStyle(
                        NotificationCompat.InboxStyle()
                            .addLine(test.title)
                            .addLine("Estimated level: ${test.levelColor.name}")
                    )
                    .setGroup(READY_GROUP)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(test.id, notification)
            }
        }

        if (onErrorTests.isNotEmpty()) {
            val errorTestGroup = NotificationCompat.Builder(this, "error_status_tests")
                .setSmallIcon(R.drawable.discord)
                .setGroup(ERROR_GROUP)
                .setGroupSummary(true)
                .build()
            notificationManager.notify(-2, errorTestGroup);

            onErrorTests.forEach { test ->
                val notification = NotificationCompat.Builder(this, "error_status_tests")
                    .setSmallIcon(R.drawable.discord)
                    .setContentTitle("Error Test Status")
                    .setStyle(NotificationCompat.BigTextStyle().bigText(test.toString()))
                    .setGroup(ERROR_GROUP)
                    .build()

                notificationManager.notify(test.id, notification)
            }
        }

        if (onLoadingTests.isNotEmpty()) {
            val currentLoadingTest = onLoadingTests[0]
            val lineText = if (currentLoadingTest.queue == 0) {
                "Tests left: ${onLoadingTests.size}"
            } else {
                "Queue: №${currentLoadingTest.queue}"
            }

            return NotificationCompat.Builder(this, "loading_status_tests")
                .setSmallIcon(R.drawable.discord)
                .setOnlyAlertOnce(true)
                .setContentTitle("Loading test №${currentLoadingTest.id}")
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .addLine(lineText)
                )
                .setProgress(100, currentLoadingTest.progress, currentLoadingTest.queue != 0)
                .build()
        }

        return null
    }
    
}