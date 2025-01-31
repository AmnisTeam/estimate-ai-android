package com.evg.charts.pie.render

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.evg.charts.pie.PieChartData
import com.evg.charts.util.FLOAT_200

/**
 * Created by iamzimin on 2025/1/31 : 18:22
 */
class CustomSliceDrawer(
    private val sliceThickness: Float = 25F,
    private val slicePaddingAngle: Float = 1F,
) : ISliceDrawer {

    init {
        require(sliceThickness in 10F..100F) {
            "Thickness must be between 10 and 100, included"
        }
    }

    private val sectorPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = PaintingStyle.Stroke
        }
    }

    private fun computeSectorThickness(area: Size): Float {
        val minSize = area.width.coerceAtMost(area.height)
        return sliceThickness * minSize / FLOAT_200
    }

    private fun computeDrawableArea(area: Size): Rect {
        val sliceThicknessOffset = computeSectorThickness(area) / 2F
        val horizontalOffset = (area.width - area.height) / 2F
        return Rect(
            left = sliceThicknessOffset + horizontalOffset,
            top = sliceThicknessOffset,
            right = area.width - sliceThicknessOffset - horizontalOffset,
            bottom = area.height - sliceThicknessOffset
        )
    }

    override fun drawSlice(
        drawScope: DrawScope,
        canvas: Canvas,
        area: Size,
        startAngle: Float,
        sweepAngle: Float,
        slice: PieChartData.Slice
    ) {
        val sliceThickness = computeSectorThickness(area)
        val drawableArea = computeDrawableArea(area)

        val adjustedSweepAngle = (sweepAngle - slicePaddingAngle).coerceAtLeast(0F)

        canvas.drawArc(
            rect = drawableArea,
            paint = sectorPaint.apply {
                color = slice.color
                strokeWidth = sliceThickness
            },
            startAngle = startAngle,
            sweepAngle = adjustedSweepAngle,
            useCenter = false
        )
    }
}
