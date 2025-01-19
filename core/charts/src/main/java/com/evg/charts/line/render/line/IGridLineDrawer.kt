package com.evg.charts.line.render.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Created by iamzimin on 2025/1/19 : 22:23
 */
interface IGridLineDrawer {

    /**
     * Draw a line
     *
     * @param drawScope the scope to draw in
     * @param canvas the canvas to draw on
     * @param start the start point of the line
     * @param end the end point of the line
     */
    fun drawLine(
        drawScope: DrawScope,
        canvas: Canvas,
        start: Offset,
        end: Offset
    )
}
