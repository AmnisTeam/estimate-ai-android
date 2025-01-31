package com.evg.statistics.presentation.chart

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.evg.charts.pie.PieChart
import com.evg.charts.pie.PieChartData
import com.evg.charts.pie.render.CustomSliceDrawer
import com.evg.charts.simpleChartAnimation
import com.evg.statistics.presentation.model.TestStatisticsUI
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.utils.extensions.toTestLevel
import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestLevelColors
import com.evg.utils.model.TestScore

@Composable
fun StylizedPieChart(
    points: List<TestStatisticsUI>,
) {
    val total = points.size.toFloat()

    val groupedPoints = points
        .sortedBy { it.testScore.score }
        .groupBy { it.testScore.level }

    val groupedSlices = groupedPoints
        .map { (level, tests) ->
            val percentage = (tests.size / total) * 100
            PieChartData.Slice(percentage, level.color)
        }
        .ifEmpty { listOf(PieChartData.Slice(100f, TestLevelColors.UNKNOWN.color)) }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        PieChart(
            modifier = Modifier
                .size(200.dp),
            pieChartData = PieChartData(
                slices = groupedSlices
            ),
            animation = simpleChartAnimation(),
            sliceDrawer = CustomSliceDrawer(),
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Column {
                groupedPoints.toList().asReversed().forEach { (testLevelColor, _) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(testLevelColor.color, shape = CircleShape)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = testLevelColor.name,
                            color = testLevelColor.color,
                            style = AppTheme.typography.small,
                        )
                    }
                }
            }
        }

        val languageAvg = points.map { it.testScore.score }.average()
        val languageLevel = if (languageAvg.isNaN()) {
            "??"
        } else {
            languageAvg.toTestLevel().name
        }

        ConstraintLayout {
            val (bottomText, centerText) = createRefs()

            Text(
                text = languageLevel,
                color = AppTheme.colors.text,
                style = AppTheme.typography.heading,
                modifier = Modifier.constrainAs(centerText) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
            )

            Text(
                modifier = Modifier.constrainAs(bottomText) {
                    centerHorizontallyTo(parent)
                    top.linkTo(centerText.bottom)
                },
                text = "average level",
                color = AppTheme.colors.textField,
                style = AppTheme.typography.small,
            )
        }
    }

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
