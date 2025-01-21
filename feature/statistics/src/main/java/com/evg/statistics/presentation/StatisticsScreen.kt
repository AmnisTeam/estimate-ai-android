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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.evg.utils.model.TestLevelColors
import com.evg.resource.R
import com.evg.statistics.presentation.chart.StylizedLineChart
import com.evg.statistics.presentation.chart.StylizedPieChart
import com.evg.statistics.presentation.model.DateTile
import com.evg.statistics.presentation.model.StatisticsUI
import com.evg.statistics.presentation.model.TestStatisticsUI
import com.evg.statistics.presentation.mvi.StatisticsState
import com.evg.utils.extensions.toTestLevel
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPaddingTile
import com.evg.ui.theme.VerticalPadding
import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestScore
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.Date
import java.util.Locale

@Composable
fun StatisticsScreen(
    state: StatisticsState,
    getStatisticsInRange: (Pair<Long, Long>) -> Unit,
) {
    val context = LocalContext.current
    val isStatisticsLoading = state.isStatisticsLoading
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
            onDateRangeSelected = getStatisticsInRange,
        )

        Spacer(modifier = Modifier.height(10.dp))



        if (tests.testStatisticsUI.size >= 2) {
            StylizedPieChart(
                points = tests.testStatisticsUI,
            )

            Spacer(modifier = Modifier.height(20.dp))

            StylizedLineChart(
                points = tests.testStatisticsUI,
            )
        }

    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StatisticsScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            StatisticsScreen(
                state = StatisticsState(
                    isStatisticsLoading = false,
                    statistics = MutableStateFlow(
                        StatisticsUI(
                            frequentLevel = TestScore(5),
                            frequentDayOfWeek = DayOfWeek.MONDAY,
                            testStatisticsUI = emptyList()
                        )
                    ),
                ),
                getStatisticsInRange = { _ -> },
            )
        }
    }
}