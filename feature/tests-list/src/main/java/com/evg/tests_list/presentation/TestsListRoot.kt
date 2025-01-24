package com.evg.tests_list.presentation

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.evg.LocalNavHostController
import com.evg.tests_list.presentation.mvi.TestsListSideEffect
import com.evg.tests_list.presentation.mvi.TestsListViewModel
import com.evg.tests_list.presentation.service.TestStatusService
import com.evg.utils.mapper.toErrorMessage
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TestsListRoot(
    viewModel: TestsListViewModel = koinViewModel(),
    bottomBar: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val navigation = LocalNavHostController.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TestsListSideEffect.ConnectTestProgressFail -> {
                Toast.makeText(context, sideEffect.error.toErrorMessage(context), Toast.LENGTH_SHORT).show()
            }
            is TestsListSideEffect.StartService -> {
                val intent = Intent(context, TestStatusService::class.java).apply {
                    action = TestStatusService.Actions.START.toString()
                }
                context.startForegroundService(intent)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TestsListScreen(
            modifier = Modifier.weight(1f),
            navigation = navigation,
            state = viewModel.collectAsState().value,
            getAllTests = viewModel::getAllTests,
        )
        bottomBar()
    }
}