package com.evg.password_reset.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.api.domain.utils.CombinedPasswordResetError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.password_reset.presentation.mvi.PasswordResetSideEffect
import com.evg.password_reset.presentation.mvi.PasswordResetViewModel
import com.evg.resource.R
import com.evg.ui.snackbar.SnackBarController
import com.evg.ui.snackbar.SnackBarEvent
import com.evg.utils.mapper.toErrorMessage
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun PasswordResetRoot(
    viewModel: PasswordResetViewModel = koinViewModel(),
    modifier: Modifier,
    onLoginScreen: () -> Unit,
) {
    val context = LocalContext.current

    val letterSend = stringResource(R.string.letter_with_instructions_send_to_email)
    val mailDoesNotExist = stringResource(R.string.email_does_not_exist)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            PasswordResetSideEffect.PasswordResetSuccess -> {
                onLoginScreen()
                SnackBarController.sendEvent(event = SnackBarEvent(message = letterSend))
            }
            is PasswordResetSideEffect.PasswordResetFail -> {
                when (sideEffect.combinedPasswordResetError) {
                    is CombinedPasswordResetError.Network -> {
                        SnackBarController.sendEvent(event = SnackBarEvent(message = sideEffect.combinedPasswordResetError.networkError.toErrorMessage(context)))
                    }
                    is CombinedPasswordResetError.PasswordReset -> {
                        when (sideEffect.combinedPasswordResetError.passwordResetError) {
                            PasswordResetError.UNKNOWN_EMAIL -> SnackBarController.sendEvent(event = SnackBarEvent(message = mailDoesNotExist))
                        }
                    }
                }
            }
        }
    }

    PasswordResetScreen(
        state = viewModel.collectAsState().value,
        modifier = modifier,
        onLoginScreen = onLoginScreen,
        passwordReset = viewModel::passwordReset,
    )
}