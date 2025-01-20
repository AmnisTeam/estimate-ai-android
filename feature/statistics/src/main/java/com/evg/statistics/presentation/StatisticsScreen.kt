package com.evg.statistics.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.evg.utils.model.TestLevelColors
import com.evg.resource.R
import com.evg.statistics.presentation.chart.StylizedLineChart
import com.evg.statistics.presentation.model.DateTile
import com.evg.statistics.presentation.model.Statistic
import com.evg.statistics.presentation.mvi.StatisticsState
import com.evg.utils.extensions.toTestLevel
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPaddingTile
import com.evg.ui.theme.VerticalPadding
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatisticsScreen(
    navigation: NavHostController,
    state: StatisticsState,
    getAllStatistics: () -> Unit,
) {
    val context = LocalContext.current
    val tests = state.statistics.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    date = DateTile.Dates.WEEK,
                ),
                DateTile(
                    dateResId = R.string.month,
                    date = DateTile.Dates.MONTH,
                ),
                DateTile(
                    dateResId = R.string.year,
                    date = DateTile.Dates.YEAR,
                ),
            ),
            onDateRangeSelected = {
                val first = it.first ?: return@DateSelection
                val second = it.second ?: return@DateSelection
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val startDateString = dateFormat.format(Date(first))
                val endDateString = dateFormat.format(Date(second))

                Toast.makeText(context, "$startDateString to $endDateString", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        val points = listOf(
            Statistic(level = 5, levelColor = TestLevelColors.A1, timestamp = 1737237000),
            Statistic(level = 9, levelColor = TestLevelColors.A1, timestamp = 1737237000 + 1000),
            Statistic(level = 30, levelColor = TestLevelColors.B1, timestamp = 1737237000 + 2000),
            Statistic(level = 40, levelColor = TestLevelColors.B2, timestamp = 1737237000 + 3000),
            Statistic(level = 50, levelColor = TestLevelColors.C1, timestamp = 1737237000 + 4000),
            Statistic(level = 60, levelColor = TestLevelColors.C2, timestamp = 1737237000 + 5000),
            Statistic(level = 80, levelColor = TestLevelColors.A1, timestamp = 1737237000 + 7000),
            Statistic(level = 90, levelColor = TestLevelColors.B1, timestamp = 1737237000 + 8000),
            Statistic(level = 100, levelColor = TestLevelColors.C2, timestamp = 1737237000 + 9000),
        )

        val pointsRand = List(10) { index ->
            val randomValue = (0..100).random()
            val stats = Statistic(
                level = randomValue,
                levelColor = randomValue.toFloat().toTestLevel(),
                timestamp = 1737237000 + index * 1000L,
            )
            println(stats)
            stats
        }

        Spacer(modifier = Modifier.height(20.dp))

        /*StylizedPieChart(
            points = points,
        )*/

        StylizedLineChart(
            points = pointsRand,
        )

    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StatisticsScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            StatisticsScreen(
                navigation = NavHostController(LocalContext.current),
                state = StatisticsState(
                    isStatisticsLoading = false,
                    statistics = MutableStateFlow(listOf()),
                ),
                getAllStatistics = {},
            )
        }
    }
}