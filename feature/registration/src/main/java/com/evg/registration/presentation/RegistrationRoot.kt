package com.evg.registration.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.evg.registration.presentation.mvi.RegistrationSideEffect
import com.evg.registration.presentation.mvi.RegistrationViewModel
import com.evg.LocalNavHostController
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun RegistrationRoot(
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navigation = LocalNavHostController.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RegistrationSideEffect.RegistrationStatus -> Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show()
        }
    }

    RegistrationScreen(
        navigation = navigation,
        registrationUser = viewModel::registrationUser,
    )
}