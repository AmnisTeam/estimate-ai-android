package com.evg.tests_list.presentation

import androidx.compose.runtime.Composable
import com.evg.LocalNavHostController
import com.evg.tests_list.presentation.mvi.TestsListViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun TestsListRoot(
    viewModel: TestsListViewModel = koinViewModel()
) {
    val navigation = LocalNavHostController.current


    TestsListScreen(
        navigation = navigation,
        state = viewModel.collectAsState().value,
        getAllTests = viewModel::getAllTests,
    )
}