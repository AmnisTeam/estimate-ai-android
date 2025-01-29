package com.evg.tests_list.presentation

import android.content.Intent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.evg.tests_list.presentation.mvi.TestsListSideEffect
import com.evg.tests_list.presentation.mvi.TestsListViewModel
import com.evg.tests_list.presentation.service.TestStatusService
import com.evg.ui.snackbar.SnackBarController
import com.evg.ui.snackbar.SnackBarEvent
import com.evg.utils.mapper.toErrorMessage
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TestsListRoot(
    viewModel: TestsListViewModel = koinViewModel(),
    modifier: Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onTestSelectScreen: () -> Unit,
    onTestEssayScreen: (Int) -> Unit,
) {
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TestsListSideEffect.ConnectTestProgressFail -> {
                SnackBarController.sendEvent(event = SnackBarEvent(message = sideEffect.error.toErrorMessage(context)))
            }
            is TestsListSideEffect.StartService -> {
                val intent = Intent(context, TestStatusService::class.java).apply {
                    action = TestStatusService.Actions.START.toString()
                }
                context.startForegroundService(intent)
            }
        }
    }

    TestsListScreen(
        state = viewModel.collectAsState().value,
        modifier = modifier,
        animatedVisibilityScope = animatedVisibilityScope,
        onTestSelectScreen = onTestSelectScreen,
        onTestEssayScreen = onTestEssayScreen,
        getAllTests = viewModel::getAllTests,
    )
}