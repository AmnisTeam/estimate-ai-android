package com.evg.statistics.presentation.chart

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.charts.line.CustomLineChart
import com.evg.charts.line.LineChartData
import com.evg.charts.line.render.line.GradientLineDrawer
import com.evg.charts.line.render.line.VerticalLineDrawer
import com.evg.charts.line.render.point.FilledCircularPointDrawer
import com.evg.charts.line.render.xaxis.CustomXAxisDrawer
import com.evg.charts.line.render.yaxis.SimpleYAxisDrawer
import com.evg.resource.R
import com.evg.statistics.presentation.model.TestStatisticsUI
import com.evg.utils.extensions.toDateString
import com.evg.utils.extensions.toTestLevel
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestScore

@Composable
fun StylizedLineChart(
    points: List<TestStatisticsUI>,
) {
    if (points.size < 3) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.not_enough_data),
                color = AppTheme.colors.text,
                style = AppTheme.typography.body,
            )
        }
    } else {
        CustomLineChart(
            modifier = Modifier
                .height(300.dp),
            lineChartData = LineChartData(
                points = points.map {
                    LineChartData.Point(it.testScore.score.toFloat(), it.createdAt.toDateString())
                },
                padBy = 0F,
                startAtZero = true,
            ),
            pointDrawer = FilledCircularPointDrawer().copy(
                color = Color.Transparent,
            ),
            lineDrawer = GradientLineDrawer(
                colors = points.map {
                    it.testScore.level.color
                },
                cornerRadius = 40f,
                strokeCap = StrokeCap.Round,
            ),
            xAxisDrawer = CustomXAxisDrawer(
                labelTextColor = AppTheme.colors.text,
                axisLineColor = Color.Transparent,
                axisLabelRotation = -45f,
                axisLabelShift = 30f,
                drawLabelEvery = 1,
            ),
            yAxisDrawer = SimpleYAxisDrawer(
                labelTextColor = AppTheme.colors.text,
                axisLineColor = Color.Transparent,
                labelValueFormatter = {
                    it.toTestLevel().name
                },
                drawLabelEvery = 5,
            ),
            verticalLineDrawer = VerticalLineDrawer(
                color = Color.DarkGray,
                thickness = 1.dp,
                dashLength = 50f,
                dashSpacing = 50f,
            ),
            horizontalOffset = 5f,
            minVerticalLineSpace = 20.dp,
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StylizedLineChartPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            val pointsRand = List(10) { index ->
                val randomValue = (0..100).random()
                TestStatisticsUI(testScore = TestScore(randomValue), type = TestIcons.ESSAY, createdAt = 1737237000 + index * 1000L)
            }

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

            StylizedLineChart(
                points = pointsRand
            )
        }
    }
}