package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.MarketDataSource
import com.example.model.RadarPair
import com.example.model.RadarSignalType
import com.example.ui.components.SparklineChart
import com.example.ui.theme.BorderOutline
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.RubyCrimson
import com.example.ui.theme.SurfaceBright
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
fun FloorCeilingRadarScreen(
  viewModel: TradingViewModel,
  modifier: Modifier = Modifier
) {
  val radarPairs by viewModel.radarPairs.collectAsState()
  val settings by viewModel.settings.collectAsState()
  val isLoading by viewModel.isLoadingRealData.collectAsState()
  val lastUpdateTime by viewModel.lastUpdateFormatted.collectAsState()
  var searchQuery by remember { mutableStateOf("") }

  val filteredPairs = if (searchQuery.isBlank()) {
    radarPairs
  } else {
    radarPairs.filter {
      it.symbol.contains(searchQuery, ignoreCase = true) ||
        it.name.contains(searchQuery, ignoreCase = true)
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(VoidDark)
      .padding(horizontal = 14.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Radar Live Telemetry Header with sweep animation
    item {
      RadarCockpitHeader(
        activePairsCount = radarPairs.size,
        threshold = settings.threshold,
        lastUpdateTime = lastUpdateTime,
        isLoading = isLoading,
        dataSource = settings.preferredDataSource,
        onRefresh = { viewModel.refreshRealMarketData() },
        onDataSourceChange = { viewModel.setMarketDataSource(it) }
      )
    }

    // Search and Quick Filter Bar
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Search Input
        Box(
          modifier = Modifier
            .weight(1f)
            .height(42.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainer)
            .border(1.dp, BorderOutline, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp),
          contentAlignment = Alignment.CenterStart
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = null,
              tint = TextSecondary,
              modifier = Modifier.size(16.dp)
            )
            BasicTextField(
              value = searchQuery,
              onValueChange = { searchQuery = it },
              singleLine = true,
              textStyle = TextStyle(
                color = TextPrimary,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif
              ),
              cursorBrush = SolidColor(EmeraldNeon),
              modifier = Modifier.weight(1f),
              decorationBox = { innerTextField ->
                if (searchQuery.isEmpty()) {
                  Text(
                    text = "جستجوی جفت‌ارز (BTC, ETH, بیت‌کوین...)",
                    color = TextMuted,
                    fontSize = 11.sp
                  )
                }
                innerTextField()
              }
            )
            if (searchQuery.isNotEmpty()) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "پاک کردن",
                tint = TextSecondary,
                modifier = Modifier
                  .size(16.dp)
                  .clickable { searchQuery = "" }
              )
            }
          }
        }
      }
    }

    if (radarPairs.isEmpty() && isLoading) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainer)
            .padding(28.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(32.dp),
              color = EmeraldNeon,
              strokeWidth = 3.dp
            )
            Text(
              text = "در حال دریافت زنده قیمت‌های بازار از CoinGecko API...",
              color = TextSecondary,
              fontSize = 12.sp
            )
          }
        }
      }
    } else {
      // List of scanned crypto pairs
      items(filteredPairs, key = { it.symbol }) { pair ->
        RadarPairCard(pair = pair)
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun RadarCockpitHeader(
  activePairsCount: Int,
  threshold: Int,
  lastUpdateTime: String,
  isLoading: Boolean,
  dataSource: MarketDataSource,
  onRefresh: () -> Unit,
  onDataSourceChange: (MarketDataSource) -> Unit
) {
  val infiniteTransition = rememberInfiniteTransition(label = "radarSweep")
  val angle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(4000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "sweepAngle"
  )

  val spinAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(1000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "spin"
  )

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(SurfaceContainerLow.copy(alpha = 0.9f))
      .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
      .padding(14.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
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
              text = "ردیاب قیمت زنده کریپتو (Live Tracker)",
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Text(
            text = "منبع فعال: ${if (dataSource == MarketDataSource.COINGECKO) "CoinGecko API" else "Binance Spot"} • آپدیت: $lastUpdateTime",
            color = TextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            lineHeight = 16.sp
          )
        }

        // Animated Mini Radar Canvas
        Box(
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(SurfaceContainer)
            .border(1.dp, EmeraldNeon.copy(alpha = 0.3f), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Canvas(modifier = Modifier.size(48.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            drawCircle(color = BorderOutline, radius = size.width / 2f, style = Stroke(1f))
            drawCircle(color = BorderOutline, radius = size.width / 3f, style = Stroke(1f))
            drawCircle(color = BorderOutline, radius = size.width / 6f, style = Stroke(1f))

            // Rotating Sweep Beam
            val rad = Math.toRadians(angle.toDouble())
            val endX = center.x + (size.width / 2f) * Math.cos(rad).toFloat()
            val endY = center.y + (size.width / 2f) * Math.sin(rad).toFloat()
            drawLine(
              brush = Brush.radialGradient(
                colors = listOf(EmeraldNeon, Color.Transparent),
                center = center,
                radius = size.width / 2f
              ),
              start = center,
              end = Offset(endX, endY),
              strokeWidth = 2.5f
            )
          }
          Icon(
            imageVector = Icons.Default.Sensors,
            contentDescription = null,
            tint = EmeraldNeon,
            modifier = Modifier.size(16.dp)
          )
        }
      }

      // Action row: Data Source Switcher + Refresh
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Toggle CoinGecko vs Binance
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainer)
            .padding(2.dp),
          horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (dataSource == MarketDataSource.COINGECKO) EmeraldNeon else Color.Transparent)
              .clickable { onDataSourceChange(MarketDataSource.COINGECKO) }
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = "CoinGecko",
              color = if (dataSource == MarketDataSource.COINGECKO) VoidDark else TextSecondary,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(if (dataSource == MarketDataSource.BINANCE) EmeraldNeon else Color.Transparent)
              .clickable { onDataSourceChange(MarketDataSource.BINANCE) }
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = "Binance",
              color = if (dataSource == MarketDataSource.BINANCE) VoidDark else TextSecondary,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }
        }

        // Live Refresh Button
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerHigh)
            .clickable(onClick = onRefresh)
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Sync,
              contentDescription = "تازه‌سازی",
              tint = ElectricCyan,
              modifier = Modifier
                .size(13.dp)
                .then(if (isLoading) Modifier.rotate(spinAngle) else Modifier)
            )
            Text(
              text = "تازه‌سازی لحظه‌ای",
              color = ElectricCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }
  }
}

@Composable
private fun RadarPairCard(pair: RadarPair) {
  val isFloor = pair.signalType == RadarSignalType.FLOOR_CONFIRMED
  val isCeiling = pair.signalType == RadarSignalType.CEILING_EXHAUSTION
  val accentColor = when {
    isFloor -> EmeraldNeon
    isCeiling -> RubyCrimson
    else -> ElectricCyan
  }

  val isPositive = pair.priceChange24h >= 0

  // Calculate range progress between 24h floor and ceiling
  val range = (pair.ceilingPrice - pair.floorPrice).coerceAtLeast(0.0001)
  val currentPos = ((pair.currentPrice - pair.floorPrice) / range).coerceIn(0.0, 1.0).toFloat()

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(SurfaceContainer)
      .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
      .padding(12.dp)
      .testTag("radar_card_${pair.symbol}")
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      // Header: Logo Image, Symbol, Name, Sparkline & Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Coin Logo Image (from CoinGecko or fallback)
          if (!pair.imageUrl.isNullOrBlank()) {
            AsyncImage(
              model = ImageRequest.Builder(LocalContext.current)
                .data(pair.imageUrl)
                .crossfade(true)
                .build(),
              contentDescription = pair.name,
              contentScale = ContentScale.Fit,
              modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
            )
          } else {
            Box(
              modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(SurfaceContainerHigh),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = pair.symbol.take(1),
                color = ElectricCyan,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            }
          }

          Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              Text(
                text = pair.symbol,
                color = TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              )
              if (pair.marketCapRank != null) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceContainerHigh)
                    .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                  Text(
                    text = "#${pair.marketCapRank}",
                    color = TextSecondary,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                  )
                }
              }
            }
            Text(
              text = pair.name,
              color = TextSecondary,
              fontSize = 11.sp
            )
          }
        }

        // Live Mini Sparkline Chart
        if (pair.sparklinePrices.isNotEmpty()) {
          SparklineChart(
            prices = pair.sparklinePrices,
            isPositive = isPositive,
            modifier = Modifier
              .width(68.dp)
              .height(26.dp)
          )
        }

        // Status Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(accentColor.copy(alpha = 0.15f))
            .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
          ) {
            Icon(
              imageVector = if (isFloor) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
              contentDescription = null,
              tint = accentColor,
              modifier = Modifier.size(11.dp)
            )
            Text(
              text = when (pair.signalType) {
                RadarSignalType.FLOOR_CONFIRMED -> "کف تاییدشده"
                RadarSignalType.CEILING_EXHAUSTION -> "سقف بحرانی"
                RadarSignalType.LIQUIDITY_SWEEP -> "هانت نقدینگی"
                RadarSignalType.SCANNING -> "پایش روند"
              },
              color = accentColor,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      // Price and 24h change
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        val formattedPrice = if (pair.currentPrice >= 1.0) {
          String.format(Locale.US, "$%,.2f", pair.currentPrice)
        } else {
          String.format(Locale.US, "$%,.4f", pair.currentPrice)
        }

        Text(
          text = formattedPrice,
          color = TextPrimary,
          fontSize = 19.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )

        Text(
          text = "${if (isPositive) "+" else ""}${String.format(Locale.US, "%.2f", pair.priceChange24h)}%",
          color = if (isPositive) EmeraldNeon else RubyCrimson,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )
      }

      // 24h Floor vs Ceiling Visual Range Bar
      Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "کف ۲۴ ساعته: $%,.2f".format(Locale.US, pair.floorPrice),
            color = EmeraldNeon,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
          )
          Text(
            text = "سقف ۲۴ ساعته: $%,.2f".format(Locale.US, pair.ceilingPrice),
            color = RubyCrimson,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
          )
        }

        LinearProgressIndicator(
          progress = { currentPos },
          modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = if (currentPos <= 0.35f) EmeraldNeon else if (currentPos >= 0.65f) RubyCrimson else ElectricCyan,
          trackColor = SurfaceContainerHigh,
          strokeCap = StrokeCap.Round
        )
      }

      // Telemetry metrics
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
              text = "تاییدات همپوشان: ${pair.activeIndicatorsCount}/۵ (${pair.confidencePercent}٪)",
              color = EmeraldNeon,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = pair.rsiDivergence,
              color = ElectricCyan,
              fontSize = 10.sp
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = pair.cvdDelta,
              color = TextSecondary,
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace,
              modifier = Modifier.weight(1f)
            )
            Text(
              text = pair.dataSource,
              color = TextMuted,
              fontSize = 9.sp,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }
    }
  }
}
