package com.evg.registration.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evg.registration.presentation.mvi.RegistrationSideEffect
import com.evg.registration.presentation.mvi.RegistrationViewModel
import com.evg.LocalNavHostController
import com.evg.api.domain.utils.CombinedRegistrationError
import com.evg.api.domain.utils.RegistrationError
import com.evg.resource.R
import com.evg.utils.mapper.toErrorMessage
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
    val errorEmailExists = stringResource(R.string.email_exists)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RegistrationSideEffect.RegistrationSuccess -> {
                navigation.navigate("login") {
                    popUpTo("registration") {
                        inclusive = true
                    }
                }
                Toast.makeText(context, registrationSuccess, Toast.LENGTH_SHORT).show()
            }
            is RegistrationSideEffect.RegistrationFail -> {
                when (sideEffect.combinedRegistrationError) {
                    is CombinedRegistrationError.Network -> {
                        Toast.makeText(context, sideEffect.combinedRegistrationError.networkError.toErrorMessage(context), Toast.LENGTH_SHORT).show()
                    }
                    is CombinedRegistrationError.Registration -> {
                        when (sideEffect.combinedRegistrationError.registrationError) {
                            RegistrationError.EMAIL_EXIST -> Toast.makeText(context, errorEmailExists, Toast.LENGTH_SHORT).show()
                        }
                    }
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