package com.evg.password_reset.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.api.domain.utils.CombinedPasswordResetError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.password_reset.presentation.mvi.PasswordResetSideEffect
import com.evg.password_reset.presentation.mvi.PasswordResetViewModel
import com.evg.resource.R
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
                Toast.makeText(context, letterSend, Toast.LENGTH_SHORT).show()
            }
            is PasswordResetSideEffect.PasswordResetFail -> {
                when (sideEffect.combinedPasswordResetError) {
                    is CombinedPasswordResetError.Network -> {
                        Toast.makeText(context, sideEffect.combinedPasswordResetError.networkError.toErrorMessage(context), Toast.LENGTH_SHORT).show()
                    }
                    is CombinedPasswordResetError.PasswordReset -> {
                        when (sideEffect.combinedPasswordResetError.passwordResetError) {
                            PasswordResetError.UNKNOWN_EMAIL -> Toast.makeText(context, mailDoesNotExist, Toast.LENGTH_SHORT).show()
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