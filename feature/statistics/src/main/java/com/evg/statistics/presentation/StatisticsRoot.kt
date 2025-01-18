package com.evg.statistics.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.evg.LocalNavHostController
import com.evg.statistics.presentation.mvi.StatisticsSideEffect
import com.evg.statistics.presentation.mvi.StatisticsViewModel
import com.evg.ui.mapper.toErrorMessage
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun StatisticsRoot(
    viewModel: StatisticsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navigation = LocalNavHostController.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is StatisticsSideEffect.StatisticsFail -> {
                Toast.makeText(context, sideEffect.error.toErrorMessage(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    StatisticsScreen(
        navigation = navigation,
        state = viewModel.collectAsState().value,
        getAllStatistics = viewModel::getAllStatistics,
    )
}