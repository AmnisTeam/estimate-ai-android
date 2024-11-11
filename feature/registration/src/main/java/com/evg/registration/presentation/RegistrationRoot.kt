package com.evg.registration.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.registration.presentation.mvi.RegistrationSideEffect
import com.evg.registration.presentation.mvi.RegistrationViewModel
import com.evg.LocalNavHostController
import com.evg.api.domain.utils.RegistrationError
import com.evg.resource.R
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun RegistrationRoot(
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navigation = LocalNavHostController.current

    val registrationSuccess = stringResource(R.string.registration_success)
    val errorRequestTimeout = stringResource(R.string.request_timeout)
    val errorTooManyRequests = stringResource(R.string.too_many_requests)
    val errorServerError = stringResource(R.string.server_error)
    val errorSerialization = stringResource(R.string.serialization_error)
    val errorEmailExists = stringResource(R.string.email_exists)
    val errorUnknown = stringResource(R.string.unknown_error)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RegistrationSideEffect.RegistrationSuccess -> {
                Toast.makeText(context, registrationSuccess, Toast.LENGTH_SHORT).show()
            }
            is RegistrationSideEffect.RegistrationFail -> {
                when (sideEffect.error) {
                    RegistrationError.REQUEST_TIMEOUT -> Toast.makeText(context, errorRequestTimeout, Toast.LENGTH_SHORT).show()
                    RegistrationError.TOO_MANY_REQUESTS -> Toast.makeText(context, errorTooManyRequests, Toast.LENGTH_SHORT).show()
                    RegistrationError.SERVER_ERROR -> Toast.makeText(context, errorServerError, Toast.LENGTH_SHORT).show()
                    RegistrationError.SERIALIZATION -> Toast.makeText(context, errorSerialization, Toast.LENGTH_SHORT).show()
                    RegistrationError.EMAIL_EXIST -> Toast.makeText(context, errorEmailExists, Toast.LENGTH_SHORT).show()
                    RegistrationError.UNKNOWN -> Toast.makeText(context, errorUnknown, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    RegistrationScreen(
        navigation = navigation,
        state = viewModel.collectAsState().value,
        registrationUser = viewModel::registrationUser,
    )
}