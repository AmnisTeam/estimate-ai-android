package com.evg.statistics.presentation.chart

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.charts.line.CustomLineChart
import com.evg.charts.line.LineChart
import com.evg.charts.line.LineChartData
import com.evg.charts.line.render.line.GradientLineDrawer
import com.evg.charts.line.render.line.VerticalLineDrawer
import com.evg.charts.line.render.point.FilledCircularPointDrawer
import com.evg.charts.line.render.xaxis.CustomXAxisDrawer
import com.evg.charts.line.render.yaxis.SimpleYAxisDrawer
/*import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import com.evg.statistics.presentation.chart.GradientLineDrawer*/
import com.evg.model.TestLevelColors
import com.evg.statistics.presentation.model.Statistic
import com.evg.ui.extensions.toDateString
import com.evg.ui.extensions.toTestLevel
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StylizedLineChart(
    points: List<Statistic>,
) {
    CustomLineChart(
        modifier = Modifier
            .height(300.dp),
        lineChartData = LineChartData(
            points = points.map {
                LineChartData.Point(it.level.toFloat(), it.timestamp.toDateString())
            },
            padBy = 0F,
            startAtZero = true,
        ),
        pointDrawer = FilledCircularPointDrawer().copy(
            color = Color.Transparent,
        ),
        lineDrawer = GradientLineDrawer(
            colors = points.map {
                it.levelColor.color
            },
            cornerRadius = 40f,
            strokeCap = StrokeCap.Round,
        ),
        xAxisDrawer = CustomXAxisDrawer(
            labelTextColor = AppTheme.colors.text,
            axisLineColor = Color.Transparent,
            axisLabelRotation = -45f,
            axisLabelShift = 30f,
            drawLabelEvery = 2,
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
    )
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StylizedLineChartPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            val pointsRand = List(10) { index ->
                val randomValue = (0..100).random()
                Statistic(
                    level = randomValue,
                    levelColor = randomValue.toFloat().toTestLevel(),
                    timestamp = 1737237000 + index * 1000L,
                )
            }

            val points = listOf(
                Statistic(level=1, levelColor=TestLevelColors.A1, timestamp=1737237000),
                Statistic(level=98, levelColor=TestLevelColors.C2, timestamp=1737238000),
                Statistic(level=21, levelColor=TestLevelColors.A2, timestamp=1737239000),
                Statistic(level=67, levelColor=TestLevelColors.C1, timestamp=1737240000),
                Statistic(level=55, levelColor=TestLevelColors.B2, timestamp=1737241000),
                Statistic(level=1,  levelColor=TestLevelColors.A1, timestamp=1737242000),
                Statistic(level=55, levelColor=TestLevelColors.B2, timestamp=1737243000),
                Statistic(level=45, levelColor=TestLevelColors.B1, timestamp=1737244000),
                Statistic(level=37, levelColor=TestLevelColors.B1, timestamp=1737245000),
                Statistic(level=5,  levelColor=TestLevelColors.A1, timestamp=1737246000),
            )

            StylizedLineChart(
                points = pointsRand
            )
        }
    }
}