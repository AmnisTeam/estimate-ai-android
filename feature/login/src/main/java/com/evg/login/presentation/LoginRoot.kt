package com.evg.login.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.LocalNavHostController
import com.evg.api.domain.utils.LoginError
import com.evg.login.presentation.mvi.LoginSideEffect
import com.evg.login.presentation.mvi.LoginViewModel
import com.evg.resource.R
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navigation = LocalNavHostController.current

    val loginSuccess = stringResource(R.string.login_success)
    val errorRequestTimeout = stringResource(R.string.request_timeout)
    val errorTooManyRequests = stringResource(R.string.too_many_requests)
    val errorServerError = stringResource(R.string.server_error)
    val errorSerialization = stringResource(R.string.serialization_error)
    val errorUnknown = stringResource(R.string.unknown_error)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            LoginSideEffect.LoginSuccess -> {
                Toast.makeText(context, loginSuccess, Toast.LENGTH_SHORT).show()
                navigation.navigate("tests") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            }
            is LoginSideEffect.LoginFail -> {
                when (sideEffect.error) {
                    LoginError.REQUEST_TIMEOUT -> Toast.makeText(context, errorRequestTimeout, Toast.LENGTH_SHORT).show()
                    LoginError.TOO_MANY_REQUESTS -> Toast.makeText(context, errorTooManyRequests, Toast.LENGTH_SHORT).show()
                    LoginError.SERVER_ERROR -> Toast.makeText(context, errorServerError, Toast.LENGTH_SHORT).show()
                    LoginError.SERIALIZATION -> Toast.makeText(context, errorSerialization, Toast.LENGTH_SHORT).show()
                    LoginError.UNKNOWN -> Toast.makeText(context, errorUnknown, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LoginScreen(
        navigation = navigation,
        state = viewModel.collectAsState().value,
        loginUser = viewModel::loginUser,
    )
}