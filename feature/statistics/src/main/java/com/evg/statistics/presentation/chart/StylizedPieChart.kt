package com.evg.statistics.presentation.chart

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.charts.pie.PieChart
import com.evg.charts.pie.PieChartData
import com.evg.charts.pie.render.SimpleSliceDrawer
import com.evg.charts.simpleChartAnimation
import com.evg.utils.model.TestLevelColors
import com.evg.statistics.presentation.model.TestStatisticsUI
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestScore

@Composable
fun StylizedPieChart(
    points: List<TestStatisticsUI>,
) {
    val total = points.size.toFloat()

    val groupedSlices = points
        .groupBy { it.testScore.level }
        .map { (level, tests) ->
            val percentage = (tests.size / total) * 100
            PieChartData.Slice(percentage, level.color)
        }

    PieChart(
        modifier = Modifier
            .size(200.dp),
        pieChartData = PieChartData(
            slices = groupedSlices
        ),
        animation = simpleChartAnimation(),
        sliceDrawer = SimpleSliceDrawer(),
    )
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StylizedPieChartPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            val points = listOf(
                TestStatisticsUI(testScore = TestScore(5), type = TestIcons.ESSAY, createdAt = 1737237000),
                TestStatisticsUI(testScore = TestScore(9), type = TestIcons.ESSAY, createdAt = 1737237000 + 1000),
                TestStatisticsUI(testScore = TestScore(30), type = TestIcons.ESSAY, createdAt = 1737237000 + 2000),
                TestStatisticsUI(testScore = TestScore(40), type = TestIcons.ESSAY, createdAt = 1737237000 + 3000),
                TestStatisticsUI(testScore = TestScore(50), type = TestIcons.ESSAY, createdAt = 1737237000 + 4000),
                TestStatisticsUI(testScore = TestScore(60), type = TestIcons.ESSAY, createdAt = 1737237000 + 5000),
                TestStatisticsUI(testScore = TestScore(80), type = TestIcons.ESSAY, createdAt = 1737237000 + 7000),
                TestStatisticsUI(testScore = TestScore(90), type = TestIcons.ESSAY, createdAt = 1737237000 + 8000),
                TestStatisticsUI(testScore = TestScore(100), type = TestIcons.ESSAY, createdAt = 1737237000 + 9000),
            )

            StylizedPieChart(
                points = points
            )
        }
    }
}
