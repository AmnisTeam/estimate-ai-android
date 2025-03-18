package com.evg.account.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.evg.account.presentation.mvi.AccountViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun AccountRoot(
    viewModel: AccountViewModel = koinViewModel(),
    modifier: Modifier,
    onLoginScreen: () -> Unit,
) {
    /*val context = LocalContext.current

    val success = stringResource(R.string.)

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {

        }
    }*/

    AccountScreen(
        modifier = modifier,
        state = viewModel.collectAsState().value,
        dispatch = viewModel::dispatch,
        onLoginScreen = onLoginScreen,
    )
}