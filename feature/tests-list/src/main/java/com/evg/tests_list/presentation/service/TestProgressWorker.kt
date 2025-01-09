package com.evg.tests_list.presentation.service

import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.usecase.ConnectTestProgressUseCase
import kotlinx.coroutines.Job
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TestProgressWorker(
    private val context: Context,
    private val params: WorkerParameters,
): CoroutineWorker(context, params), KoinComponent {
    private val connectTestProgressUseCase: ConnectTestProgressUseCase by inject()

    override suspend fun doWork(): Result {
        when (val result = connectTestProgressUseCase.invoke()) {
            is ServerResult.Success -> {
                result.data.collect { tests: List<TestType> ->
                    if (tests.isNotEmpty()) {
                        val intent = Intent(applicationContext, TestStatusService::class.java).apply {
                            action = TestStatusService.Actions.START.toString()
                        }
                        applicationContext.startForegroundService(intent)
                    } else {
                        val intent = Intent(applicationContext, TestStatusService::class.java).apply {
                            action = TestStatusService.Actions.STOP.toString()
                        }
                        applicationContext.stopService(intent)
                    }
                }
            }
            is ServerResult.Error -> {
                return Result.failure()
            }
        }

        return Result.success()
    }
}