package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.theme.AmberGold
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.RubyCrimson
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VoidDark

private const val LOGO_URL =
  "https://lh3.googleusercontent.com/aida/AEtjO1VRP49M2IzfrC3Z3A9C70LJ4hba5SYL1ITsOnVeOdw1e5-WL12bxtIVOfaOHpa_LN4cAZ5H719xKz-QACNN1-D9FEN5R6s8ebXjRXqzmp24watwGfrCDDtpXccTMrGNB8gXT-LWHyGXyM09LSqkAy75gsI7lXQjFpuwEZct6-ovzZA-dM2FxBZE3SuMDCm6M1jvChH_tMGhta_EE4Iif7l_NpislSULKmXxaERBHoxbRPfJNIGprP2h-HA"

@Composable
fun TopHeader(
  unreadNotificationsCount: Int = 2,
  onNotificationsClick: () -> Unit = {},
  onProfileClick: () -> Unit = {},
  onRefreshClick: () -> Unit = {},
  isRefreshing: Boolean = false,
  isLiveConnected: Boolean = true,
  lastUpdateTime: String = "همین الان",
  subtitleText: String = "Alert Settings"
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.35f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(800),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )

  val rotationAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "spin"
  )

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(VoidDark.copy(alpha = 0.94f))
      .padding(horizontal = 14.dp, vertical = 10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Right side in RTL: Logo & Branding
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // App / Brand Icon
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerLow),
          contentAlignment = Alignment.Center
        ) {
          AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
              .data(LOGO_URL)
              .crossfade(true)
              .error(R.drawable.app_logo_1791016149469)
              .placeholder(R.drawable.app_logo_1791016149469)
              .build(),
            contentDescription = "AlphaFloor Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(30.dp)
          )
        }

        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
          ) {
            Text(
              text = "AlphaFloor",
              color = TextPrimary,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = (-0.3).sp
            )
            Text(
              text = "|",
              color = TextMuted,
              fontSize = 12.sp
            )
            Text(
              text = "رادار کف و سقف",
              color = ElectricCyan,
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium
            )
          }

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(if (isLiveConnected) EmeraldNeon.copy(alpha = pulseAlpha) else AmberGold)
            )
            Text(
              text = "ONLINE SCANNER",
              color = if (isLiveConnected) EmeraldNeon else AmberGold,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              letterSpacing = 0.5.sp
            )
            Text(
              text = "•",
              color = TextMuted,
              fontSize = 10.sp
            )
            Text(
              text = if (isRefreshing) "در حال دریافت قیمت‌ها..." else "زنده ($lastUpdateTime)",
              color = TextSecondary,
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Normal
            )
          }
        }
      }

      // Left side in RTL: Refresh, Notification and Profile Avatar
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // Live Refresh Button
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerLow)
            .clickable(onClick = onRefreshClick)
            .testTag("refresh_real_data_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Sync,
            contentDescription = "به‌روزرسانی قیمت‌های لحظه‌ای",
            tint = if (isRefreshing) EmeraldNeon else ElectricCyan,
            modifier = Modifier
              .size(17.dp)
              .then(if (isRefreshing) Modifier.rotate(rotationAngle) else Modifier)
          )
        }

        // Notification bell
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerLow)
            .clickable(onClick = onNotificationsClick)
            .testTag("notifications_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "سیستم هشدارها",
            tint = TextPrimary,
            modifier = Modifier.size(18.dp)
          )
          if (unreadNotificationsCount > 0) {
            Box(
              modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 6.dp, end = 6.dp)
                .size(7.dp)
                .clip(CircleShape)
                .background(RubyCrimson)
            )
          }
        }

        // Profile Avatar
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(EmeraldLight)
            .clickable(onClick = onProfileClick)
            .testTag("profile_avatar_button"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "پروفایل کاربر",
            tint = VoidDark,
            modifier = Modifier.size(19.dp)
          )
        }
      }
    }
  }
}
