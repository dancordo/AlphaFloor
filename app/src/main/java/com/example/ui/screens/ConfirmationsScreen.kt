package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.RadialProgressMeter
import com.example.ui.theme.BorderOutline
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldNeon
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VoidDark
import com.example.viewmodel.TradingViewModel

data class IndicatorMetric(
  val titleFa: String,
  val titleEn: String,
  val accuracyRate: Float,
  val weightPercent: String,
  val description: String
)

@Composable
fun ConfirmationsScreen(
  viewModel: TradingViewModel,
  modifier: Modifier = Modifier
) {
  val settings by viewModel.settings.collectAsState()

  val indicatorsMetrics = listOf(
    IndicatorMetric(
      titleFa = "واگرایی هوشمند اندیکاتورها",
      titleEn = "RSI & MACD Divergence Cluster",
      accuracyRate = 0.931f,
      weightPercent = "۲۵٪ وزن ماتریس",
      description = "رهگیری واگرایی‌های معمولی و مخفی در ۳ تایم‌فریم همزمان (۱۵ دقیقه، ۱ ساعته، ۴ ساعته)"
    ),
    IndicatorMetric(
      titleFa = "حجم و جذب معاملات دلتا",
      titleEn = "CVD & Volume Absorption Radar",
      accuracyRate = 0.918f,
      weightPercent = "۲۵٪ وزن ماتریس",
      description = "محاسبه عدم تعادل خرید و فروش تجمعی و تشخیص جذب پنهان توسط بازارگردانان"
    ),
    IndicatorMetric(
      titleFa = "هانت نقدینگی و پرایس اکشن",
      titleEn = "Liquidity Sweeps & Smart Money",
      accuracyRate = 0.885f,
      weightPercent = "۲۰٪ وزن ماتریس",
      description = "شناسایی جمع‌آوری استاپ‌های معامله‌گران خرد و بازگشت سریع به کانال تعادلی"
    ),
    IndicatorMetric(
      titleFa = "سفارشات عمده دفترچه سفارش",
      titleEn = "Whale Depth & Wall Detection",
      accuracyRate = 0.899f,
      weightPercent = "۱۵٪ وزن ماتریس",
      description = "آنالیز عمق دفتر سفارشات در ۱۰ صرافی برتر و اعتبارسنجی دیوارهای اسپات واقعی"
    ),
    IndicatorMetric(
      titleFa = "الگوهای برگشتی کندل استیک",
      titleEn = "Reversal Candlestick Matrix",
      accuracyRate = 0.862f,
      weightPercent = "۱۵٪ وزن ماتریس",
      description = "پایش الگوهای چکش، پین‌بار و اینگالفینگ با فیلتر بسته شدن بدنه کندل"
    )
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(VoidDark)
      .padding(horizontal = 14.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Header Overview
    item {
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
          Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = EmeraldNeon,
                modifier = Modifier.size(18.dp)
              )
              Text(
                text = "ماتریس اعتبارسنجی همپوشان",
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
            }
            Text(
              text = "آمار دقت و نرخ موفقیت تک‌تک الگوریتم‌ها در بازگشت‌های قیمتی",
              color = TextSecondary,
              fontSize = 11.sp,
              lineHeight = 16.sp
            )
            Text(
              text = "حالت جاری: حداقل ${settings.threshold} تاییدیه همزمان",
              color = ElectricCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }

          RadialProgressMeter(
            progress = 0.894f,
            label = "۸۹.۴٪",
            size = 56.dp,
            strokeWidth = 4.dp
          )
        }
      }
    }

    // Stats Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainer)
            .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
              text = "حذف فیک‌بریک‌آوت",
              color = TextSecondary,
              fontSize = 11.sp
            )
            Text(
              text = "۹۶.۴٪",
              color = EmeraldNeon,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "مسدودسازی پرش جعلی",
              color = TextMuted,
              fontSize = 10.sp
            )
          }
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainer)
            .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
              text = "میانگین ریسک به ریوارد",
              color = TextSecondary,
              fontSize = 11.sp
            )
            Text(
              text = "۱ : ۳.۵",
              color = ElectricCyan,
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "در سودهای محقق شده",
              color = TextMuted,
              fontSize = 10.sp
            )
          }
        }
      }
    }

    // Individual Metric Breakdown Cards
    items(indicatorsMetrics.size) { idx ->
      val metric = indicatorsMetrics[idx]
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(SurfaceContainer)
          .border(1.dp, BorderOutline, RoundedCornerShape(12.dp))
          .padding(12.dp)
          .testTag("metric_card_$idx")
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
              Text(
                text = metric.titleFa,
                color = TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = metric.titleEn,
                color = TextSecondary,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(SurfaceContainerHigh)
                .padding(horizontal = 7.dp, vertical = 3.dp)
            ) {
              Text(
                text = "%.1f%%".format(metric.accuracyRate * 100),
                color = EmeraldNeon,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              )
            }
          }

          // Progress Bar
          LinearProgressIndicator(
            progress = { metric.accuracyRate },
            modifier = Modifier
              .fillMaxWidth()
              .height(5.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = EmeraldNeon,
            trackColor = SurfaceContainerHigh,
            strokeCap = StrokeCap.Round
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = metric.description,
              color = TextSecondary,
              fontSize = 10.sp,
              lineHeight = 15.sp,
              modifier = Modifier.weight(1f)
            )
            Text(
              text = metric.weightPercent,
              color = ElectricCyan,
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace,
              modifier = Modifier.padding(start = 6.dp)
            )
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
