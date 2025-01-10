package com.evg.login.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.LocalNavHostController
import com.evg.api.domain.utils.CombinedLoginError
import com.evg.api.domain.utils.LoginError
import com.evg.login.presentation.mvi.LoginSideEffect
import com.evg.login.presentation.mvi.LoginViewModel
import com.evg.resource.R
import com.evg.ui.mapper.toErrorMessage
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
    val errorWrongEmailOrPassword = stringResource(R.string.wrong_email_or_password)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            LoginSideEffect.LoginSuccess -> {
                navigation.navigate("tests-list") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
                Toast.makeText(context, loginSuccess, Toast.LENGTH_SHORT).show()
            }
            is LoginSideEffect.LoginFail -> {
                when (sideEffect.combinedLoginError) {
                    is CombinedLoginError.Network -> {
                        Toast.makeText(context, sideEffect.combinedLoginError.networkError.toErrorMessage(context), Toast.LENGTH_SHORT).show()
                    }
                    is CombinedLoginError.Login -> {
                        when (sideEffect.combinedLoginError.loginError) {
                            LoginError.WRONG_EMAIL_OR_PASS -> Toast.makeText(context, errorWrongEmailOrPassword, Toast.LENGTH_SHORT).show()
                        }
                    }
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