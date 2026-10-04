package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DispatchChannel
import com.example.ui.components.CyberSwitch
import com.example.ui.components.RadialProgressMeter
import com.example.ui.theme.BorderOutline
import com.example.ui.theme.BorderOutlineActive
import com.example.ui.theme.CyanFixed
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.RubyCrimson
import com.example.ui.theme.SurfaceBright
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VoidDark
import com.example.viewmodel.TradingViewModel

@Composable
fun AlertSettingsScreen(
  viewModel: TradingViewModel,
  modifier: Modifier = Modifier
) {
  val settings by viewModel.settings.collectAsState()
  val isTestingAudio by viewModel.isTestingAudio.collectAsState()
  val isSaving by viewModel.isSaving.collectAsState()
  val saveMessage by viewModel.saveMessage.collectAsState()
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(VoidDark)
      .verticalScroll(scrollState)
      .padding(horizontal = 14.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {

    // --- 1. Top Cockpit Header Panel ---
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(SurfaceContainerLow.copy(alpha = 0.95f))
        .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Right side in RTL: Anti-Noise badge
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
              text = "ANTI-NOISE ENGINE V4.2",
              color = EmeraldNeon,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              letterSpacing = 0.5.sp
            )
          }

          // Left side in RTL: Noise shield active status
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(SurfaceContainer)
              .padding(horizontal = 8.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Bolt,
              contentDescription = null,
              tint = ElectricCyan,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = "سپر نویز فعال",
              color = ElectricCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        Text(
          text = "تنظیمات اختصاصی اعتبارسنجی الگوریتم و هشدارها",
          color = TextPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          lineHeight = 24.sp
        )

        Text(
          text = "فیلترهای همگرایی چندگانه برای مسدودسازی شکست‌های فیک، پرش‌های جعلی و هانت استاپ‌لاس در سقف و کف بازار.",
          color = TextSecondary,
          fontSize = 12.sp,
          lineHeight = 18.sp
        )
      }
    }

    // --- 2. Live Performance Verification Banner ---
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(
          Brush.horizontalGradient(
            colors = listOf(
              SurfaceContainerHigh,
              SurfaceContainer,
              SurfaceContainerLow
            )
          )
        )
        .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
          ) {
            Icon(
              imageVector = Icons.Default.VerifiedUser,
              contentDescription = null,
              tint = EmeraldNeon,
              modifier = Modifier.size(17.dp)
            )
            Text(
              text = "آمار اعتبارسنجی زنده",
              color = EmeraldNeon,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }

          Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "۸۹.۴٪",
              color = TextPrimary,
              fontSize = 24.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.SansSerif
            )
            Text(
              text = "نرخ موفقیت",
              color = TextSecondary,
              fontSize = 12.sp,
              modifier = Modifier.padding(bottom = 3.dp)
            )
          }

          Text(
            text = "در معاملات تاییدشده با ۴+ شاخص همپوشان هوشمند",
            color = TextSecondary,
            fontSize = 11.sp,
            lineHeight = 16.sp
          )
        }

        // Live Micro Radial Progress Ring (+4)
        RadialProgressMeter(
          progress = 0.894f,
          label = "+${settings.threshold}",
          size = 54.dp,
          strokeWidth = 4.dp
        )
      }
    }

    // --- 3. SECTION 1: Minimum Confirmation Threshold ---
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(SurfaceContainerLow.copy(alpha = 0.9f))
        .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Section Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Tune,
              contentDescription = null,
              tint = ElectricCyan,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "حداقل آستانه تأییدیه",
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(SurfaceContainerHigh)
              .padding(horizontal = 7.dp, vertical = 2.dp)
          ) {
            Text(
              text = "STRICT-MATRIX",
              color = CyanFixed,
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Option 1: 3 Confirmations (Normal)
        val isOpt3Selected = settings.threshold == 3
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isOpt3Selected) SurfaceContainerHigh else SurfaceContainer)
            .border(
              width = 1.dp,
              color = if (isOpt3Selected) ElectricCyan.copy(alpha = 0.4f) else Color.Transparent,
              shape = RoundedCornerShape(12.dp)
            )
            .clickable { viewModel.setThreshold(3) }
            .padding(12.dp)
            .testTag("threshold_3_option")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
          ) {
            // Radio circle indicator
            Box(
              modifier = Modifier
                .padding(top = 2.dp)
                .size(16.dp)
                .clip(CircleShape)
                .background(if (isOpt3Selected) ElectricCyan else SurfaceContainerHighest),
              contentAlignment = Alignment.Center
            ) {
              if (isOpt3Selected) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(VoidDark)
                )
              }
            }

            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(
                  text = "حداقل ۳ تأییدیه",
                  color = TextPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(SurfaceContainerHigh)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "حالت متوازن (Normal)",
                    color = TextSecondary,
                    fontSize = 10.sp
                  )
                }
              }
              Text(
                text = "تعداد سیگنال‌های متوسط با حساسیت معقول به تغییر روند.",
                color = TextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
              )
            }
          }
        }

        // Option 2: 4 Confirmations (Strict / Recommended)
        val isOpt4Selected = settings.threshold == 4
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isOpt4Selected) SurfaceContainerHigh else SurfaceContainer)
            .then(
              if (isOpt4Selected) {
                Modifier.shadow(elevation = 12.dp, shape = RoundedCornerShape(12.dp), ambientColor = EmeraldNeon, spotColor = EmeraldNeon)
              } else {
                Modifier
              }
            )
            .border(
              width = 1.dp,
              color = if (isOpt4Selected) BorderOutlineActive else Color.Transparent,
              shape = RoundedCornerShape(12.dp)
            )
            .clickable { viewModel.setThreshold(4) }
            .padding(12.dp)
            .testTag("threshold_4_option")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
          ) {
            // Radio circle indicator
            Box(
              modifier = Modifier
                .padding(top = 2.dp)
                .size(16.dp)
                .clip(CircleShape)
                .background(if (isOpt4Selected) EmeraldNeon else SurfaceContainerHighest),
              contentAlignment = Alignment.Center
            ) {
              if (isOpt4Selected) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(VoidDark)
                )
              }
            }

            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Text(
                  text = "حداقل ۴ تأییدیه",
                  color = TextPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(EmeraldNeon)
                    .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "فوق سخت‌گیرانه (پیشنهادی)",
                    color = EmeraldDark,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
              Text(
                text = "حذف قاطعانه فیک‌بریک‌آوت‌ها؛ تنها معاملات با احتمال بازگشت قطعی.",
                color = TextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
              )
            }
          }
        }

        // Explanatory Terminal Box
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerLowest)
            .padding(10.dp)
        ) {
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = EmeraldNeon,
              modifier = Modifier
                .padding(top = 2.dp)
                .size(16.dp)
            )
            Text(
              text = "با فعال‌سازی حالت فوق سخت‌گیرانه، فقط زمانی هشدار لانگ در کف یا شورت در سقف ارسال می‌شود که همگرایی نقدینگی، حجم و ساختار همزمان تایید شوند.",
              color = TextSecondary,
              fontSize = 11.sp,
              lineHeight = 17.sp
            )
          }
        }
      }
    }

    // --- 4. SECTION 2: Active Verification Indicators (Toggles) ---
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(SurfaceContainerLow.copy(alpha = 0.9f))
        .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.FactCheck,
              contentDescription = null,
              tint = EmeraldNeon,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "شاخص‌های اعتبارسنجی فعال",
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(SurfaceContainer)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${settings.activeIndicatorsCount} از ۵ فعال",
              color = EmeraldNeon,
              fontSize = 11.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Text(
          text = "الگوریتم‌های محاسباتی برای تایید نهایی کف و سقف‌های قیمتی:",
          color = TextSecondary,
          fontSize = 12.sp
        )

        // Item 1: Divergence
        IndicatorToggleItem(
          title = "واگرایی هوشمند اندیکاتورها",
          subTitle = "RSI & MACD Divergence Cluster",
          icon = Icons.Default.ShowChart,
          iconTint = ElectricCyan,
          checked = settings.divergence,
          onCheckedChange = { viewModel.toggleDivergence() },
          tag = "toggle_divergence"
        )

        // Item 2: CVD & Volume
        IndicatorToggleItem(
          title = "حجم و جذب معاملات دلتا",
          subTitle = "CVD & Volume Absorption Radar",
          icon = Icons.Default.BarChart,
          iconTint = EmeraldNeon,
          checked = settings.cvdVolume,
          onCheckedChange = { viewModel.toggleCvdVolume() },
          tag = "toggle_cvd"
        )

        // Item 3: Liquidity Sweeps
        IndicatorToggleItem(
          title = "هانت نقدینگی و پرایس اکشن",
          subTitle = "Liquidity Sweeps & Smart Money",
          icon = Icons.AutoMirrored.Filled.TrendingUp,
          iconTint = ElectricCyan,
          checked = settings.liquiditySweeps,
          onCheckedChange = { viewModel.toggleLiquiditySweeps() },
          tag = "toggle_liquidity"
        )

        // Item 4: Candlestick Reversal
        IndicatorToggleItem(
          title = "الگوهای برگشتی کندل استیک",
          subTitle = "Reversal Candlestick Matrix",
          icon = Icons.Default.Tune,
          iconTint = EmeraldLight,
          checked = settings.reversalCandles,
          onCheckedChange = { viewModel.toggleReversalCandles() },
          tag = "toggle_reversal"
        )

        // Item 5: Whale Depth
        IndicatorToggleItem(
          title = "سفارشات عمده دفترچه سفارش",
          subTitle = "Whale Depth & Wall Detection",
          icon = Icons.Default.Layers,
          iconTint = ElectricCyan,
          checked = settings.whaleDepth,
          onCheckedChange = { viewModel.toggleWhaleDepth() },
          tag = "toggle_whale_depth"
        )
      }
    }

    // --- 5. SECTION 3: Notification Dispatch Routing ---
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(SurfaceContainerLow.copy(alpha = 0.9f))
        .border(1.dp, BorderOutline, RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Campaign,
              contentDescription = null,
              tint = ElectricCyan,
              modifier = Modifier.size(19.dp)
            )
            Text(
              text = "نوع و مجرای دریافت نوتیفیکیشن",
              color = TextPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Text(
            text = "REALTIME",
            color = TextSecondary,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
          )
        }

        // Long Alert
        NotificationToggleItem(
          title = "هشدار صوتی جهش از کف (Long Alert)",
          subTitle = "آژیر فوری با فرکانس بالا در شکار کف مطلق",
          icon = Icons.Default.ArrowUpward,
          iconTint = EmeraldNeon,
          checked = settings.longAlert,
          onCheckedChange = { viewModel.toggleLongAlert() },
          tag = "toggle_long_alert"
        )

        // Short Alert
        NotificationToggleItem(
          title = "نوتیفیکیشن سقف بحرانی (Short Alert)",
          subTitle = "اخطار ریزش بهنگام اتمام نقدینگی خریداران",
          icon = Icons.Default.ArrowDownward,
          iconTint = RubyCrimson,
          checked = settings.shortAlert,
          onCheckedChange = { viewModel.toggleShortAlert() },
          tag = "toggle_short_alert"
        )

        // Routing Channel Selector
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text(
            text = "کانال ارسال اطلاعات:",
            color = TextSecondary,
            fontSize = 11.sp
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            // Direct Phone Push
            val isPhoneActive = settings.dispatchChannel == DispatchChannel.PHONE_DIRECT
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isPhoneActive) SurfaceContainerHigh else SurfaceContainer)
                .border(
                  width = 1.dp,
                  color = if (isPhoneActive) BorderOutlineActive else Color.Transparent,
                  shape = RoundedCornerShape(12.dp)
                )
                .then(
                  if (isPhoneActive) {
                    Modifier.shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp), ambientColor = EmeraldNeon, spotColor = EmeraldNeon)
                  } else {
                    Modifier
                  }
                )
                .clickable { viewModel.setDispatchChannel(DispatchChannel.PHONE_DIRECT) }
                .padding(vertical = 10.dp, horizontal = 8.dp)
                .testTag("channel_phone_button"),
              contentAlignment = Alignment.Center
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Smartphone,
                  contentDescription = null,
                  tint = if (isPhoneActive) EmeraldNeon else TextSecondary,
                  modifier = Modifier.size(18.dp)
                )
                Text(
                  text = "پوش مستقیم گوشی",
                  color = if (isPhoneActive) EmeraldNeon else TextSecondary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            // Telegram VIP Bot
            val isTelegramActive = settings.dispatchChannel == DispatchChannel.TELEGRAM_VIP
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isTelegramActive) SurfaceContainerHigh else SurfaceContainer)
                .border(
                  width = 1.dp,
                  color = if (isTelegramActive) BorderOutlineActive else Color.Transparent,
                  shape = RoundedCornerShape(12.dp)
                )
                .then(
                  if (isTelegramActive) {
                    Modifier.shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp), ambientColor = EmeraldNeon, spotColor = EmeraldNeon)
                  } else {
                    Modifier
                  }
                )
                .clickable { viewModel.setDispatchChannel(DispatchChannel.TELEGRAM_VIP) }
                .padding(vertical = 10.dp, horizontal = 8.dp)
                .testTag("channel_telegram_button"),
              contentAlignment = Alignment.Center
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Send,
                  contentDescription = null,
                  tint = if (isTelegramActive) EmeraldNeon else TextSecondary,
                  modifier = Modifier.size(17.dp)
                )
                Text(
                  text = "ربات تلگرام VIP",
                  color = if (isTelegramActive) EmeraldNeon else TextSecondary,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }

    // --- 6. Audio Test & Save Action Buttons ---
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Test Alarm Button
      Box(
        modifier = Modifier
          .height(48.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(SurfaceContainerHigh)
          .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
          .clickable { viewModel.triggerAudioTest(context) }
          .padding(horizontal = 14.dp)
          .testTag("test_audio_button"),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          if (isTestingAudio) {
            CircularProgressIndicator(
              modifier = Modifier.size(16.dp),
              color = ElectricCyan,
              strokeWidth = 2.dp
            )
            Text(
              text = "پخش صوت...",
              color = ElectricCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          } else {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = null,
              tint = ElectricCyan,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "تست زنگ",
              color = ElectricCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      // Save & Activate Button
      Box(
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .shadow(elevation = 16.dp, shape = RoundedCornerShape(12.dp), ambientColor = EmeraldNeon, spotColor = EmeraldNeon)
          .clip(RoundedCornerShape(12.dp))
          .background(EmeraldNeon)
          .clickable { viewModel.saveSettings() }
          .padding(horizontal = 12.dp)
          .testTag("save_settings_button"),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          if (isSaving) {
            CircularProgressIndicator(
              modifier = Modifier.size(18.dp),
              color = VoidDark,
              strokeWidth = 2.dp
            )
            Text(
              text = "در حال ذخیره‌سازی...",
              color = VoidDark,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          } else if (saveMessage != null) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = VoidDark,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "تنظیمات ذخیره شد!",
              color = VoidDark,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          } else {
            Icon(
              imageVector = Icons.Default.Save,
              contentDescription = null,
              tint = VoidDark,
              modifier = Modifier.size(19.dp)
            )
            Text(
              text = "ذخیره و فعال‌سازی فیلترها",
              color = VoidDark,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}

@Composable
private fun IndicatorToggleItem(
  title: String,
  subTitle: String,
  icon: ImageVector,
  iconTint: Color,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  tag: String
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(SurfaceContainer)
      .padding(10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerHigh),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(19.dp)
          )
        }

        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
          Text(
            text = title,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )
          Text(
            text = subTitle,
            color = TextSecondary,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
          )
        }
      }

      CyberSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        tag = tag
      )
    }
  }
}

@Composable
private fun NotificationToggleItem(
  title: String,
  subTitle: String,
  icon: ImageVector,
  iconTint: Color,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  tag: String
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(SurfaceContainer)
      .padding(10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerHigh),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(19.dp)
          )
        }

        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
          Text(
            text = title,
            color = TextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
          Text(
            text = subTitle,
            color = TextSecondary,
            fontSize = 10.sp
          )
        }
      }

      CyberSwitch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        tag = tag
      )
    }
  }
}
