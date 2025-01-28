package com.evg.test_essay.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.resource.R
import com.evg.test_essay.presentation.mvi.TestEssaySideEffect
import com.evg.test_essay.presentation.mvi.TestEssayViewModel
import com.evg.ui.snackbar.SnackBarController
import com.evg.ui.snackbar.SnackBarEvent
import com.evg.utils.mapper.toErrorMessage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TestEssayRoot(
    viewModel: TestEssayViewModel,
    modifier: Modifier,
    onTestsListScreen: () -> Unit,
) {
    val context = LocalContext.current

    val replySent = stringResource(id = R.string.reply_sent)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TestEssaySideEffect.TestEssayFail -> {
                SnackBarController.sendEvent(event = SnackBarEvent(message = sideEffect.error.toErrorMessage(context)))
            }
            is TestEssaySideEffect.TestEssaySuccess -> {
                SnackBarController.sendEvent(event = SnackBarEvent(message = replySent))
                onTestsListScreen()
            }
            is TestEssaySideEffect.TestDataFail -> {
                SnackBarController.sendEvent(event = SnackBarEvent(message = sideEffect.error.toErrorMessage(context)))
            }
        }
    }

    TestEssayScreen(
        state = viewModel.collectAsState().value,
        modifier = modifier,
        sendTest = viewModel::sendTest,
        isEditable = viewModel.isEditable,
    )
}