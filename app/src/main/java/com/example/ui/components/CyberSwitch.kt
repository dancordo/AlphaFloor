package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TextMuted

@Composable
fun CyberSwitch(
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  tag: String = "cyber_switch"
) {
  val trackColor by animateColorAsState(
    targetValue = if (checked) EmeraldNeon else SurfaceContainerHighest,
    animationSpec = tween(durationMillis = 200),
    label = "trackColor"
  )

  val thumbColor by animateColorAsState(
    targetValue = if (checked) SurfaceContainerLowest else TextMuted,
    animationSpec = tween(durationMillis = 200),
    label = "thumbColor"
  )

  val thumbOffset by animateDpAsState(
    targetValue = if (checked) 24.dp else 0.dp,
    animationSpec = tween(durationMillis = 200),
    label = "thumbOffset"
  )

  val interactionSource = remember { MutableInteractionSource() }

  Box(
    modifier = modifier
      .width(52.dp)
      .height(28.dp)
      .then(
        if (checked) {
          Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(14.dp), ambientColor = EmeraldNeon, spotColor = EmeraldNeon)
        } else {
          Modifier
        }
      )
      .clip(RoundedCornerShape(14.dp))
      .background(trackColor)
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(bounded = false, radius = 24.dp),
        onClick = { onCheckedChange(!checked) }
      )
      .padding(3.dp)
      .testTag(tag),
    contentAlignment = Alignment.CenterStart
  ) {
    Box(
      modifier = Modifier
        .offset(x = thumbOffset)
        .size(22.dp)
        .clip(CircleShape)
        .background(thumbColor)
    )
  }
}
