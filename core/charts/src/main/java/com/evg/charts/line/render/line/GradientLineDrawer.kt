package com.evg.charts.line.render.line

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by iamzimin on 2025/1/19 : 20:15
 */
data class GradientLineDrawer(
    val thickness: Dp = 3.dp,
    val colors: List<Color> = listOf(Color.Cyan, Color.Magenta),
    val cornerRadius: Float = 0f,
    val strokeCap: StrokeCap = StrokeCap.Butt,
) : ILineDrawer {
    private val mPaint by lazy {
        Paint().apply {
            style = PaintingStyle.Stroke
            isAntiAlias = true
            this.strokeCap = this@GradientLineDrawer.strokeCap
        }
    }

    override fun drawLine(
        drawScope: DrawScope,
        canvas: Canvas,
        linePath: Path
    ) {
        val lineThickness = with(drawScope) {
            thickness.toPx()
        }

        val pathEffect = PathEffect.cornerPathEffect(cornerRadius)
        mPaint.pathEffect = pathEffect

        val shader = LinearGradientShader(
            from = linePath.getBounds().centerLeft,
            to = linePath.getBounds().centerRight,
            colors = colors
        )

        canvas.drawPath(
            path = linePath,
            paint = mPaint.apply {
                strokeWidth = lineThickness
                this.shader = shader
            }
        )
    }
}