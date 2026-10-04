package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SignalDirection
import com.example.model.TradingSignal
import com.example.ui.theme.BorderOutline
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.RubyCrimson
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VoidDark
import com.example.viewmodel.TradingViewModel
import java.util.Locale

@Composable
fun SignalsScreen(
  viewModel: TradingViewModel,
  modifier: Modifier = Modifier
) {
  val signals by viewModel.signals.collectAsState()
  val settings by viewModel.settings.collectAsState()
  val isLoading by viewModel.isLoadingRealData.collectAsState()
  val lastUpdateTime by viewModel.lastUpdateFormatted.collectAsState()
  var filterDirection by remember { mutableStateOf<SignalDirection?>(null) }

  val filteredSignals = if (filterDirection == null) {
    signals
  } else {
    signals.filter { it.type == filterDirection }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(VoidDark)
      .padding(horizontal = 14.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Header & Filter Chips
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerLow.copy(alpha = 0.9f))
            .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
            .padding(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(EmeraldNeon)
                )
                Text(
                  text = "سیگنال‌های قطعی کف و سقف",
                  color = TextPrimary,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              Text(
                text = "محاسبه زنده بر اساس قیمت‌های اسپات و فیلتر ${settings.threshold} تاییدیه",
                color = TextSecondary,
                fontSize = 11.sp
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(EmeraldNeon.copy(alpha = 0.15f))
                .border(1.dp, EmeraldNeon.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "LIVE TICK: $lastUpdateTime",
                color = EmeraldNeon,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }

        // Filter selector: All / Long / Short
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          FilterTabChip(
            label = "همه سیگنال‌ها (${signals.size})",
            selected = filterDirection == null,
            onClick = { filterDirection = null },
            modifier = Modifier.weight(1f)
          )
          FilterTabChip(
            label = "شکار کف (Long)",
            selected = filterDirection == SignalDirection.LONG,
            onClick = { filterDirection = SignalDirection.LONG },
            activeColor = EmeraldNeon,
            modifier = Modifier.weight(1f)
          )
          FilterTabChip(
            label = "سقف بحرانی (Short)",
            selected = filterDirection == SignalDirection.SHORT,
            onClick = { filterDirection = SignalDirection.SHORT },
            activeColor = RubyCrimson,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    if (filteredSignals.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainer)
            .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
            .padding(20.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Radar,
              contentDescription = null,
              tint = ElectricCyan,
              modifier = Modifier.size(36.dp)
            )
            Text(
              text = "در حال اسکن مداوم قیمت‌های زنده بازار",
              color = TextPrimary,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "با توجه به فعال بودن فیلتر فوق‌سخت‌گیرانه (${settings.threshold} تاییدیه همزمان)، شکست‌های فیک مسدود شده‌اند. به محض لمس کف یا سقف توسط جفت‌ارزها با همگرایی کامل، هشدار فوری صادر می‌شود.",
              color = TextSecondary,
              fontSize = 12.sp,
              lineHeight = 18.sp
            )

            if (settings.threshold == 4) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(SurfaceContainerHigh)
                  .clickable { viewModel.setThreshold(3) }
                  .padding(horizontal = 12.dp, vertical = 6.dp)
              ) {
                Text(
                  text = "تغییر آستانه به حالت متوازن (حداقل ۳ تاییدیه)",
                  color = EmeraldNeon,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    } else {
      // Signals List
      items(filteredSignals, key = { it.id }) { sig ->
        SignalCard(signal = sig)
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun FilterTabChip(
  label: String,
  selected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  activeColor: Color = ElectricCyan
) {
  Box(
    modifier = modifier
      .height(36.dp)
      .clip(RoundedCornerShape(10.dp))
      .background(if (selected) SurfaceContainerHigh else SurfaceContainer)
      .border(
        width = 1.dp,
        color = if (selected) activeColor.copy(alpha = 0.5f) else Color.Transparent,
        shape = RoundedCornerShape(10.dp)
      )
      .clickable(onClick = onClick),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = label,
      color = if (selected) activeColor else TextSecondary,
      fontSize = 11.sp,
      fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
    )
  }
}

@Composable
private fun SignalCard(signal: TradingSignal) {
  val isLong = signal.type == SignalDirection.LONG
  val directionColor = if (isLong) EmeraldNeon else RubyCrimson

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(SurfaceContainer)
      .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
      .padding(13.dp)
      .testTag("signal_card_${signal.id}")
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      // Header: Pair, Direction Pill, Time ago
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Direction Badge
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(directionColor)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
              Icon(
                imageVector = if (isLong) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                contentDescription = null,
                tint = VoidDark,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = if (isLong) "LONG (کف)" else "SHORT (سقف)",
                color = VoidDark,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              )
            }
          }

          Text(
            text = signal.symbol,
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }

        Text(
          text = signal.timeAgo,
          color = TextMuted,
          fontSize = 11.sp
        )
      }

      // Trading Levels Matrix (Entry, TP1, TP2, SL) with real prices
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(SurfaceContainerHigh)
          .padding(8.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "ورود: $%,.2f".format(Locale.US, signal.entryPrice),
              color = TextPrimary,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "حد ضرر (SL): $%,.2f".format(Locale.US, signal.stopLoss),
              color = RubyCrimson,
              fontSize = 11.sp,
              fontFamily = FontFamily.Monospace
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "تارگت ۱: $%,.2f".format(Locale.US, signal.target1),
              color = EmeraldNeon,
              fontSize = 11.sp,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "تارگت ۲: $%,.2f".format(Locale.US, signal.target2),
              color = EmeraldNeon,
              fontSize = 11.sp,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }

      // Rationale & Indicators Confluence
      Text(
        text = signal.rationale,
        color = TextSecondary,
        fontSize = 11.sp,
        lineHeight = 16.sp
      )

      // Footer: Status and Risk/Reward
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = EmeraldNeon,
            modifier = Modifier.size(13.dp)
          )
          Text(
            text = signal.status,
            color = EmeraldNeon,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = "تاییدیه ${signal.confluenceScore} فاکتوره",
            color = ElectricCyan,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
          )
          Text(
            text = "R:R ${signal.riskReward}",
            color = TextSecondary,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
          )
        }
      }
    }
  }
}
