package com.evg.charts.line.render.xaxis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evg.charts.AxisLabelFormatter
import com.evg.charts.toLegacyInt
import com.evg.charts.util.FLOAT_1_5

/**
 * Created by iamzimin on 2025/1/19 : 21:52
 */
data class CustomXAxisDrawer(
    val labelTextSize: TextUnit = 12.sp,
    val labelTextColor: Color = Color.Black,
    val drawLabelEvery: Int = 1,// draw label text every $drawLabelEvery, like 1, 2, 3 and so on.
    val axisLabelRotation: Float = 0f,
    val axisLabelShift: Float = 0f,
    val axisLineThickness: Dp = 1.dp,
    val axisLineColor: Color = Color.Black,
    val axisLabelFormatter: AxisLabelFormatter = { value -> "$value" }
) : IXAxisDrawer {
    private val mAxisLinePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = axisLineColor
            style = PaintingStyle.Stroke
        }
    }

    private val mTextPaint by lazy {
        android.graphics.Paint().apply {
            isAntiAlias = true
            color = labelTextColor.toLegacyInt()
        }
    }

    override fun requireHeight(drawScope: DrawScope): Float = with(drawScope) {
        FLOAT_1_5 * (labelTextSize.toPx() + axisLineThickness.toPx() + axisLabelShift)
    }

    override fun drawXAxisLine(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect
    ) {
        with(drawScope) {
            val lineThickness = axisLineThickness.toPx()
            val y = drawableArea.top + lineThickness / 2F

            canvas.drawLine(
                p1 = Offset(x = drawableArea.left, y = y),
                p2 = Offset(x = drawableArea.right, y = y),
                paint = mAxisLinePaint.apply { strokeWidth = lineThickness })
        }
    }

    override fun drawXAxisLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        labels: List<*>
    ) {
        with(drawScope) {
            val labelPaint = mTextPaint.apply {
                textSize = labelTextSize.toPx()
                textAlign = android.graphics.Paint.Align.CENTER
            }

            val totalWidth = drawableArea.width
            val labelWidth = labelTextSize.toPx() * 3f
            val maxLabelCount = (totalWidth / labelWidth).toInt()

            val step = if (labels.size > maxLabelCount) {
                (labels.size / maxLabelCount) + 1
            } else {
                1
            }

            val filteredLabels = mutableListOf<Any>()
            for (i in labels.indices step step) {
                labels[i]?.let { filteredLabels.add(it) }
            }

            val labelIncrements = totalWidth / (filteredLabels.size - 1).coerceAtLeast(1).toFloat()

            filteredLabels.forEachIndexed { index, label ->
                val labelValue = axisLabelFormatter(label)
                val x = drawableArea.left + labelIncrements * index
                val y = drawableArea.bottom - axisLabelShift

                canvas.nativeCanvas.save()
                canvas.nativeCanvas.rotate(axisLabelRotation, x, y)
                canvas.nativeCanvas.drawText(labelValue, x, y, labelPaint)
                canvas.nativeCanvas.restore()
            }
        }
    }
}
