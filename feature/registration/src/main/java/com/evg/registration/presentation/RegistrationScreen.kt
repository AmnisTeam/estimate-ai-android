package com.evg.registration.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.evg.registration.domain.model.User
import com.evg.registration.presentation.mvi.RegistrationSideEffect
import com.evg.registration.presentation.mvi.RegistrationViewModel
import com.evg.ui.theme.EstimateAITheme
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val context = LocalContext.current
    //val state = viewModel.collectAsState().value

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RegistrationSideEffect.RegistrationStatus -> Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show()
        }
    }

    Text(text = "Hello")
    Button(onClick = {
        viewModel.registrationUser(user = User(email = "qwe", password = "zxc"))
    }) {
    }

}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun RegistrationScreenPreview() {
    EstimateAITheme {
        RegistrationScreen()
    }
}