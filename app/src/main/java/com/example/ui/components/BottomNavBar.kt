package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VoidDark

data class NavItem(
  val id: Int,
  val label: String,
  val icon: ImageVector,
  val tag: String
)

@Composable
fun BottomNavBar(
  selectedTab: Int,
  onTabSelected: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  // In RTL, the items placed in order [Signals, Confirmations, Radar, Filter & Alert]
  // will appear: Signals on rightmost, Confirmations second, Radar third, Filter & Alert on leftmost.
  // This matches the screenshot perfectly!
  val items = listOf(
    NavItem(0, "سیگنال‌ها", Icons.Default.BarChart, "nav_signals"),
    NavItem(1, "تأییدیه‌ها", Icons.Default.Verified, "nav_confirmations"),
    NavItem(2, "اسکنر کف/سقف", Icons.Default.TrackChanges, "nav_radar"),
    NavItem(3, "فیلتر و هشدار", Icons.Default.Tune, "nav_filter_alerts")
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(VoidDark.copy(alpha = 0.95f))
      .navigationBarsPadding()
      .height(68.dp)
      .padding(horizontal = 8.dp, vertical = 6.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      items.forEach { item ->
        val isSelected = selectedTab == item.id
        val contentColor by animateColorAsState(
          targetValue = if (isSelected) EmeraldNeon else TextMuted,
          label = "navColor"
        )

        Box(
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
              if (isSelected) SurfaceContainer.copy(alpha = 0.8f) else VoidDark
            )
            .clickable(
              onClick = { onTabSelected(item.id) }
            )
            .testTag(item.tag),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = item.icon,
              contentDescription = item.label,
              tint = contentColor,
              modifier = Modifier.size(22.dp)
            )
            Text(
              text = item.label,
              color = contentColor,
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              modifier = Modifier.padding(top = 2.dp)
            )
          }
        }
      }
    }
  }
}
