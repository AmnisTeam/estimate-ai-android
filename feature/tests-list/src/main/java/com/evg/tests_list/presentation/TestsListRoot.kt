package com.evg.tests_list.presentation

import android.content.Intent
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.evg.LocalNavHostController
import com.evg.api.domain.utils.CombinedLoginError
import com.evg.api.domain.utils.LoginError
import com.evg.tests_list.presentation.mvi.TestsListSideEffect
import com.evg.tests_list.presentation.mvi.TestsListViewModel
import com.evg.tests_list.presentation.service.TestStatusService
import com.evg.ui.mapper.toErrorMessage
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TestsListRoot(
    viewModel: TestsListViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navigation = LocalNavHostController.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TestsListSideEffect.ConnectTestProgressFail -> {
                Toast.makeText(context, sideEffect.error.toErrorMessage(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    TestsListScreen(
        navigation = navigation,
        state = viewModel.collectAsState().value,
        getAllTests = viewModel::getAllTests,
    )
}