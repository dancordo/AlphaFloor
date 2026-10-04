package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.RubyCrimson

@Composable
fun SparklineChart(
  prices: List<Float>,
  isPositive: Boolean,
  modifier: Modifier = Modifier,
  lineColor: Color = if (isPositive) EmeraldNeon else RubyCrimson,
  strokeWidth: Dp = 1.8.dp
) {
  if (prices.size < 2) return

  Canvas(modifier = modifier) {
    val minPrice = prices.minOrNull() ?: 0f
    val maxPrice = prices.maxOrNull() ?: 1f
    val priceRange = (maxPrice - minPrice).coerceAtLeast(0.0001f)

    val width = size.width
    val height = size.height

    val stepX = width / (prices.size - 1)

    val linePath = Path()
    val fillPath = Path()

    prices.forEachIndexed { index, price ->
      val x = index * stepX
      val normalizedY = (price - minPrice) / priceRange
      val y = height - (normalizedY * (height * 0.85f)) - (height * 0.08f)

      if (index == 0) {
        linePath.moveTo(x, y)
        fillPath.moveTo(x, height)
        fillPath.lineTo(x, y)
      } else {
        linePath.lineTo(x, y)
        fillPath.lineTo(x, y)
      }
    }

    fillPath.lineTo(width, height)
    fillPath.close()

    // Draw gradient fill below sparkline
    drawPath(
      path = fillPath,
      brush = Brush.verticalGradient(
        colors = listOf(lineColor.copy(alpha = 0.22f), Color.Transparent),
        startY = 0f,
        endY = height
      )
    )

    // Draw sparkline stroke
    drawPath(
      path = linePath,
      color = lineColor,
      style = Stroke(width = strokeWidth.toPx())
    )
  }
}
