package com.evg.charts.line.render.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by iamzimin on 2025/1/19 : 22:38
 */
data class VerticalLineDrawer(
    val color: Color = Color.Gray,
    val thickness: Dp = 1.dp,
    val dashLength: Float = 10f,
    val dashSpacing: Float = 5f
) : IGridLineDrawer {

    private val mPaint by lazy {
        Paint().apply {
            this.color = this@VerticalLineDrawer.color
            style = PaintingStyle.Stroke
            isAntiAlias = true
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(dashLength, dashSpacing),
                phase = 0f
            )
        }
    }

    override fun drawLine(
        drawScope: DrawScope,
        canvas: Canvas,
        start: Offset,
        end: Offset
    ) {
        val lineThickness = with(drawScope) {
            thickness.toPx()
        }

        canvas.drawLine(
            p1 = start,
            p2 = end,
            paint = mPaint.apply { strokeWidth = lineThickness }
        )
    }
}