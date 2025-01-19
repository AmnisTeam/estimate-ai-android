package com.evg.charts.line

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import com.evg.charts.line.render.line.EmptyLineShader
import com.evg.charts.line.render.line.IGridLineDrawer
import com.evg.charts.line.render.line.ILineDrawer
import com.evg.charts.line.render.line.ILineShader
import com.evg.charts.line.render.line.SolidLineDrawer
import com.evg.charts.line.render.line.VerticalLineDrawer
import com.evg.charts.line.render.point.FilledCircularPointDrawer
import com.evg.charts.line.render.point.IPointDrawer
import com.evg.charts.line.render.xaxis.IXAxisDrawer
import com.evg.charts.line.render.xaxis.SimpleXAxisDrawer
import com.evg.charts.line.render.yaxis.IYAxisDrawer
import com.evg.charts.line.render.yaxis.SimpleYAxisDrawer
import com.evg.charts.simpleChartAnimation

/**
 * Created by iamzimin on 2025/1/19 : 22:45
 */
@Composable
fun CustomLineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: IPointDrawer = FilledCircularPointDrawer(),
    lineDrawer: ILineDrawer = SolidLineDrawer(),
    lineShader: ILineShader = EmptyLineShader,
    xAxisDrawer: IXAxisDrawer = SimpleXAxisDrawer(),
    yAxisDrawer: IYAxisDrawer = SimpleYAxisDrawer(),
    verticalLineDrawer: IGridLineDrawer = VerticalLineDrawer(),
    horizontalOffset: Float = 5F,
) {
    check(horizontalOffset in 0F..25F) {
        "Horizontal Offset is the percentage offset from side, and must be between 0 and 25, included."
    }
    val transitionAnimation = remember(lineChartData.points) {
        Animatable(initialValue = 0F)
    }

    LaunchedEffect(lineChartData.points) {
        transitionAnimation.snapTo(0F)
        transitionAnimation.animateTo(1F, animationSpec = animation)
    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        drawIntoCanvas { canvas ->
            val yAxisDrawableArea = computeYAxisDrawableArea(
                xAxisLabelSize = xAxisDrawer.requireHeight(this),
                size = size
            )
            val xAxisDrawableArea = computeXAxisDrawableArea(
                yAxisWidth = yAxisDrawableArea.width,
                labelHeight = xAxisDrawer.requireHeight(this),
                size = size
            )
            val xAxisLabelsDrawableArea = computeXAxisLabelsDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                offset = horizontalOffset
            )

            val chartDrawableArea = computeDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                yAxisDrawableArea = yAxisDrawableArea,
                size = size,
                offset = horizontalOffset
            )

            lineChartData.points.forEachIndexed { index, point ->
                val x = computePointLocation(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    point = point,
                    index = index
                ).x
                verticalLineDrawer.drawLine(
                    drawScope = this,
                    canvas = canvas,
                    start = Offset(x, chartDrawableArea.top),
                    end = Offset(x, chartDrawableArea.bottom)
                )
            }

            lineDrawer.drawLine(
                drawScope = this,
                canvas = canvas,
                linePath = computeLinePath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )
            lineShader.fillLine(
                drawScope = this,
                canvas = canvas,
                fillPath = computeFillPath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )
            lineChartData.points.forEachIndexed { index, point ->
                withProgress(
                    index = index,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                ) {
                    pointDrawer.drawPoint(
                        drawScope = this,
                        canvas = canvas,
                        center = computePointLocation(
                            drawableArea = chartDrawableArea,
                            lineChartData = lineChartData,
                            point = point,
                            index = index
                        )
                    )
                }
            }

            xAxisDrawer.drawXAxisLine(
                drawScope = this,
                drawableArea = xAxisDrawableArea,
                canvas = canvas
            )
            xAxisDrawer.drawXAxisLabels(
                drawScope = this,
                canvas = canvas,
                drawableArea = xAxisLabelsDrawableArea,
                labels = lineChartData.points.map { it.label })
            yAxisDrawer.drawAxisLine(
                drawScope = this,
                drawableArea = yAxisDrawableArea,
                canvas = canvas
            )
            yAxisDrawer.drawAxisLabels(
                drawScope = this,
                canvas = canvas,
                drawableArea = yAxisDrawableArea,
                minValue = lineChartData.minY,
                maxValue = lineChartData.maxY
            )
        }
    }
}

@Preview
@Composable
private fun CustomLineChartPreview() = CustomLineChart(
    lineChartData = LineChartData(
        points = listOf(
            LineChartData.Point(1f, "Line 1"),
            LineChartData.Point(2f, "Line 2"),
            LineChartData.Point(3f, "Line 3"),
            LineChartData.Point(4f, "Line 4"),
            LineChartData.Point(1f, "Line 5"),
            LineChartData.Point(8f, "Line 6"),
            LineChartData.Point(1f, "Line 7")
        )
    )
)
