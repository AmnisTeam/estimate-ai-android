package com.evg.test_essay.presentation

import androidx.compose.runtime.Composable
import com.evg.LocalNavHostController
import com.evg.test_essay.presentation.mvi.TestEssayViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun TestEssayRoot(
    viewModel: TestEssayViewModel = koinViewModel()
) {
    val navigation = LocalNavHostController.current


    TestEssayScreen(
        navigation = navigation,
        state = viewModel.collectAsState().value,
    )
}