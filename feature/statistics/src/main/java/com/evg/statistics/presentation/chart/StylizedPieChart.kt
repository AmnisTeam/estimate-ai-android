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
import com.evg.statistics.presentation.model.Statistic
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme

@Composable
fun StylizedPieChart(
    points: List<Statistic>,
) {
    PieChart(
        modifier = Modifier
            .size(200.dp),
        pieChartData = PieChartData(
            slices = listOf(
                PieChartData.Slice(20F, TestLevelColors.A1.color),
                PieChartData.Slice(20F, TestLevelColors.A2.color),
                PieChartData.Slice(20F, TestLevelColors.B1.color),
                PieChartData.Slice(20F, TestLevelColors.B2.color),
                PieChartData.Slice(10F, TestLevelColors.C1.color),
                PieChartData.Slice(10F, TestLevelColors.C2.color),
            )
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
            StylizedPieChart(
                points = listOf(
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
            )
        }
    }
}
