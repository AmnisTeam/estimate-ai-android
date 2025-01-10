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
import com.evg.model.TestIcons
import com.evg.resource.R
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.presentation.mapper.toTestState
import com.evg.tests_list.presentation.model.TestState


class TestStatusService : Service() {
    companion object {
        private const val LOADING_TEST_ID = Int.MAX_VALUE
        private const val LOADING_GROUP = "com.evg.tests_list.LOADING_GROUP"
        private const val READY_GROUP = "com.evg.tests_list.READY_GROUP"
        private const val ERROR_GROUP = "com.evg.tests_list.ERROR_GROUP"
    }
    enum class Actions {
        START, UPDATE, STOP,
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.START.toString() -> {
                val tests: List<TestState>? = intent.parcelableArrayList<TestType>("tests")?.map { it.toTestState() }
                tests?.let { start(tests = tests) }
            }
            Actions.UPDATE.toString() -> {
                val tests: List<TestState>? = intent.parcelableArrayList<TestType>("tests")?.map { it.toTestState() }
                tests?.let { updateNotification(tests = tests) }
            }
            Actions.STOP.toString() -> {
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(tests: List<TestState>) {
        val notification = processTests(tests) ?: return

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
                .setGroup(LOADING_GROUP)
                .build()
        }

        return null
    }

    private inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }
}