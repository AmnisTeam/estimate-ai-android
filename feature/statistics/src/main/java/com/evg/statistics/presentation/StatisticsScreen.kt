package com.evg.statistics.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.statistics.presentation.chart.StylizedLineChart
import com.evg.statistics.presentation.chart.StylizedPieChart
import com.evg.statistics.presentation.mapper.reduceSize
import com.evg.statistics.presentation.mapper.toStringLocale
import com.evg.statistics.presentation.model.DateRange
import com.evg.statistics.presentation.model.DateTile
import com.evg.statistics.presentation.model.StatisticsUI
import com.evg.statistics.presentation.model.TestStatisticsUI
import com.evg.statistics.presentation.model.dateRangeSaver
import com.evg.statistics.presentation.mvi.StatisticsState
import com.evg.statistics.presentation.mvi.StatisticsViewModel
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPaddingTile
import com.evg.ui.theme.VerticalPadding
import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestScore
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.DayOfWeek

@Composable
fun StatisticsScreen(
    state: StatisticsState,
    modifier: Modifier = Modifier,
    getAllStatistics: (DateRange) -> Unit,
    getStatisticsInRange: (DateRange) -> Unit,
) {
    val context = LocalContext.current
    val refreshingState = rememberSwipeRefreshState(isRefreshing = false)
    var currentTimeRange by rememberSaveable(stateSaver = dateRangeSaver) {
        mutableStateOf(StatisticsViewModel.defaultSelect)
    }

    val isStatisticsLoading = state.isStatisticsLoading
    val tests = state.statistics.collectAsState().value


    Column(
        modifier = modifier
            .padding(
                horizontal = HorizontalPaddingTile,
                vertical = VerticalPadding,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DateSelection(
            dates = listOf(
                DateTile(
                    dateResId = R.string.week,
                    date = DateRange.Week,
                ),
                DateTile(
                    dateResId = R.string.month,
                    date = DateRange.Month,
                ),
                DateTile(
                    dateResId = R.string.year,
                    date = DateRange.Year,
                ),
            ),
            selected = currentTimeRange,
            onDateRangeSelected = { newDateRange ->
                currentTimeRange = newDateRange
                getStatisticsInRange(newDateRange)
            }
        )

        Spacer(modifier = Modifier.height(VerticalPadding))

        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize(),
            state = refreshingState,
            onRefresh = { getAllStatistics(currentTimeRange) },
            indicator = { indicatorState, indicatorTrigger ->
                SwipeRefreshIndicator(
                    state = indicatorState,
                    refreshTriggerDistance = indicatorTrigger,
                    backgroundColor = AppTheme.colors.background,
                    contentColor = AppTheme.colors.primary,
                )
            },
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if (isStatisticsLoading) Arrangement.Center else Arrangement.Top
            ) {
                if (isStatisticsLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp),
                        color = AppTheme.colors.primary,
                    )
                } else {
                    StylizedPieChart(
                        points = tests.testStatisticsUI,
                    )

                    Spacer(modifier = Modifier.height(VerticalPadding))

                    InfoGridScreen(
                        tiles = listOf(
                            Pair(stringResource(R.string.frequent_level), tests.frequentLevel?.level?.name),
                            Pair(stringResource(R.string.frequent_day), tests.frequentDayOfWeek?.toStringLocale(context)),
                        )
                    )

                    Spacer(modifier = Modifier.height(VerticalPadding + 10.dp))

                    StylizedLineChart(
                        points = tests.testStatisticsUI.reduceSize(),
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StatisticsScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            val pointsRand = List(10) { index ->
                val randomValue = (0..100).random()
                TestStatisticsUI(testScore = TestScore(randomValue), type = TestIcons.ESSAY, createdAt = 1737237000 + index * 1000L)
            }

            StatisticsScreen(
                state = StatisticsState(
                    isStatisticsLoading = false,
                    statistics = MutableStateFlow(
                        StatisticsUI(
                            frequentLevel = TestScore(5),
                            frequentDayOfWeek = DayOfWeek.MONDAY,
                            testStatisticsUI = pointsRand,
                        )
                    ),
                ),
                getAllStatistics = { _ -> },
                getStatisticsInRange = { _ -> },
            )
        }
    }
}