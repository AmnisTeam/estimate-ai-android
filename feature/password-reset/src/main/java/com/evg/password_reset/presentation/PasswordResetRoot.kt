package com.evg.password_reset.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.LocalNavHostController
import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.password_reset.presentation.mvi.PasswordResetSideEffect
import com.evg.password_reset.presentation.mvi.PasswordResetViewModel
import com.evg.resource.R
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun PasswordResetRoot(
    viewModel: PasswordResetViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navigation = LocalNavHostController.current

    val registrationSuccess = stringResource(R.string.registration_success)
    val errorRequestTimeout = stringResource(R.string.request_timeout)
    val errorTooManyRequests = stringResource(R.string.too_many_requests)
    val errorServerError = stringResource(R.string.server_error)
    val errorSerialization = stringResource(R.string.serialization_error)
    val errorUnknown = stringResource(R.string.unknown_error)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            PasswordResetSideEffect.PasswordResetSuccess -> {
                Toast.makeText(context, registrationSuccess, Toast.LENGTH_SHORT).show()
                /*navigation.navigate("login") {
                    popUpTo("registration") {
                        inclusive = true
                    }
                }*/
            }
            is PasswordResetSideEffect.PasswordResetFail -> {
                when (sideEffect.error) {
                    PasswordResetError.REQUEST_TIMEOUT -> Toast.makeText(context, errorRequestTimeout, Toast.LENGTH_SHORT).show()
                    PasswordResetError.TOO_MANY_REQUESTS -> Toast.makeText(context, errorTooManyRequests, Toast.LENGTH_SHORT).show()
                    PasswordResetError.SERVER_ERROR -> Toast.makeText(context, errorServerError, Toast.LENGTH_SHORT).show()
                    PasswordResetError.SERIALIZATION -> Toast.makeText(context, errorSerialization, Toast.LENGTH_SHORT).show()
                    PasswordResetError.UNKNOWN -> Toast.makeText(context, errorUnknown, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    PasswordResetScreen(
        navigation = navigation,
        state = viewModel.collectAsState().value,
        passwordReset = viewModel::passwordReset,
    )
}