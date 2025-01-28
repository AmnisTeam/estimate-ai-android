package com.evg.statistics.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.evg.statistics.presentation.mvi.StatisticsSideEffect
import com.evg.statistics.presentation.mvi.StatisticsViewModel
import com.evg.ui.snackbar.SnackBarController
import com.evg.ui.snackbar.SnackBarEvent
import com.evg.utils.mapper.toErrorMessage
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun StatisticsRoot(
    viewModel: StatisticsViewModel = koinViewModel(),
    modifier: Modifier,
) {
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is StatisticsSideEffect.StatisticsFail -> {
                SnackBarController.sendEvent(event = SnackBarEvent(message = sideEffect.error.toErrorMessage(context)))
            }
        }
    }

    StatisticsScreen(
        state = viewModel.collectAsState().value,
        modifier = modifier,
        getAllStatistics = viewModel::getAllStatistics,
        getStatisticsInRange = viewModel::getStatisticsInRange,
    )
}