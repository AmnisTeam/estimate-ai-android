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
import java.util.Objects

class TestProgressWorker(
    context: Context,
    params: WorkerParameters,
): CoroutineWorker(context, params), KoinComponent {
    private val connectTestProgressUseCase: ConnectTestProgressUseCase by inject()
    private var isServiceStarted = false

    override suspend fun doWork(): Result {
        val intent = Intent(applicationContext, TestStatusService::class.java)
        when (val result = connectTestProgressUseCase.invoke()) {
            is ServerResult.Success -> {
                result.data.collect { tests: List<TestType> ->
                    if (!isServiceStarted && tests.isNotEmpty()) {
                        intent.apply {
                            action = TestStatusService.Actions.START.toString()
                        }
                        intent.putParcelableArrayListExtra("tests", ArrayList(tests))
                        applicationContext.startForegroundService(intent)
                        isServiceStarted = true
                    } else if (isServiceStarted && tests.isNotEmpty()) {
                        intent.apply {
                            action = TestStatusService.Actions.UPDATE.toString()
                        }
                        intent.putParcelableArrayListExtra("tests", ArrayList(tests))
                        applicationContext.startForegroundService(intent)
                    } else {
                        intent.apply {
                            action = TestStatusService.Actions.STOP.toString()
                        }
                        applicationContext.stopService(intent)
                        isServiceStarted = false
                    }
                }
                return Result.success()
            }
            is ServerResult.Error -> {
                return Result.failure()
            }
        }
    }
}