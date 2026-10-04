package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.SurfaceContainerHighest

@Composable
fun RadialProgressMeter(
  progress: Float = 0.894f,
  label: String = "+4",
  size: Dp = 56.dp,
  strokeWidth: Dp = 4.5.dp,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = progress,
    animationSpec = tween(durationMillis = 1000),
    label = "progress"
  )

  Box(
    modifier = modifier.size(size),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.size(size)) {
      val diameter = size.toPx()
      val stroke = strokeWidth.toPx()
      val radius = (diameter - stroke) / 2f
      val topLeft = Offset(stroke / 2f, stroke / 2f)
      val arcSize = Size(radius * 2, radius * 2)

      // Background Track
      drawCircle(
        color = SurfaceContainerHighest,
        radius = radius,
        center = center,
        style = Stroke(width = stroke)
      )

      // Active Neon Arc
      drawArc(
        color = EmeraldNeon,
        startAngle = -90f,
        sweepAngle = animatedProgress * 360f,
        useCenter = false,
        topLeft = topLeft,
        size = arcSize,
        style = Stroke(width = stroke, cap = StrokeCap.Round)
      )
    }

    Text(
      text = label,
      color = EmeraldNeon,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Monospace
    )
  }
}
