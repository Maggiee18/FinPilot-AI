package com.example.finpilotai.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DonutSlice(val valuePaise: Long, val color: Color)

/**
 * Lightweight donut chart drawn with Compose Canvas (no chart library needed yet).
 * Values stay in paise; conversion to Float only happens for drawing angles.
 * The legend next to the chart carries the exact numbers for accessibility.
 */
@Composable
fun DonutChart(
    slices: List<DonutSlice>,
    contentDescription: String,
    modifier: Modifier = Modifier,
    diameter: Dp = 128.dp,
    thickness: Dp = 20.dp,
    trackColor: Color = Color.LightGray.copy(alpha = 0.3f),
) {
    val total = slices.sumOf { it.valuePaise }
    Canvas(
        modifier = modifier
            .size(diameter)
            .semantics { this.contentDescription = contentDescription },
    ) {
        val strokePx = thickness.toPx()
        val arcDiameter = size.minDimension - strokePx
        val topLeft = Offset((size.width - arcDiameter) / 2f, (size.height - arcDiameter) / 2f)
        val arcSize = Size(arcDiameter, arcDiameter)

        drawArc(trackColor, 0f, 360f, useCenter = false, topLeft = topLeft, size = arcSize, style = Stroke(strokePx))
        if (total <= 0L) return@Canvas

        val gapDegrees = if (slices.count { it.valuePaise > 0 } > 1) 2f else 0f
        var startAngle = -90f
        slices.filter { it.valuePaise > 0 }.forEach { slice ->
            val sweep = 360f * (slice.valuePaise.toDouble() / total.toDouble()).toFloat()
            drawArc(
                color = slice.color,
                startAngle = startAngle + gapDegrees / 2f,
                sweepAngle = (sweep - gapDegrees).coerceAtLeast(0.5f),
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Butt),
            )
            startAngle += sweep
        }
    }
}
