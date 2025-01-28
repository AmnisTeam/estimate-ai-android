package com.evg.test_essay.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.resource.R
import com.evg.test_essay.presentation.mvi.TestEssaySideEffect
import com.evg.test_essay.presentation.mvi.TestEssayViewModel
import com.evg.utils.mapper.toErrorMessage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TestEssayRoot(
    viewModel: TestEssayViewModel,
    modifier: Modifier,
    onTestsListScreen: () -> Unit,
    onBackScreen: () -> Unit,
) {
    val context = LocalContext.current

    val replySent = stringResource(id = R.string.reply_sent)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TestEssaySideEffect.TestEssayFail -> {
                Toast.makeText(context, sideEffect.error.toErrorMessage(context), Toast.LENGTH_SHORT).show()
            }
            is TestEssaySideEffect.TestEssaySuccess -> {
                Toast.makeText(context, replySent, Toast.LENGTH_SHORT).show()
                onTestsListScreen()
            }
            is TestEssaySideEffect.TestDataFail -> {
                Toast.makeText(context, sideEffect.error.toErrorMessage(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    TestEssayScreen(
        state = viewModel.collectAsState().value,
        modifier = modifier,
        onBackScreen = onBackScreen,
        sendTest = viewModel::sendTest,
        isEditable = viewModel.isEditable,
    )
}