package com.evg.statistics.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.evg.statistics.presentation.mvi.StatisticsSideEffect
import com.evg.statistics.presentation.mvi.StatisticsViewModel
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
                Toast.makeText(context, sideEffect.error.toErrorMessage(context), Toast.LENGTH_SHORT).show()
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