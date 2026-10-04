package com.example.model

enum class DispatchChannel {
  PHONE_DIRECT,
  TELEGRAM_VIP
}

enum class SignalDirection {
  LONG,
  SHORT
}

enum class RadarSignalType {
  FLOOR_CONFIRMED,
  CEILING_EXHAUSTION,
  SCANNING,
  LIQUIDITY_SWEEP
}

enum class MarketDataSource {
  COINGECKO,
  BINANCE
}

data class FilterSettings(
  val threshold: Int = 4,
  val divergence: Boolean = true,
  val cvdVolume: Boolean = true,
  val liquiditySweeps: Boolean = true,
  val reversalCandles: Boolean = true,
  val whaleDepth: Boolean = true,
  val longAlert: Boolean = true,
  val shortAlert: Boolean = true,
  val dispatchChannel: DispatchChannel = DispatchChannel.PHONE_DIRECT,
  val telegramChatId: String = "@AlphaFloor_VIP_Signals",
  val telegramBotConfigured: Boolean = true,
  val noiseShieldActive: Boolean = true,
  val preferredDataSource: MarketDataSource = MarketDataSource.COINGECKO
) {
  val activeIndicatorsCount: Int
    get() = listOf(divergence, cvdVolume, liquiditySweeps, reversalCandles, whaleDepth).count { it }
}

data class RadarPair(
  val symbol: String,
  val name: String,
  val currentPrice: Double,
  val priceChange24h: Double,
  val signalType: RadarSignalType,
  val floorPrice: Double,
  val ceilingPrice: Double,
  val activeIndicatorsCount: Int,
  val confidencePercent: Double,
  val cvdDelta: String,
  val rsiDivergence: String,
  val imageUrl: String? = null,
  val sparklinePrices: List<Float> = emptyList(),
  val marketCapRank: Int? = null,
  val marketCap: Double? = null,
  val volume24h: Double? = null,
  val dataSource: String = "CoinGecko API"
)

data class TradingSignal(
  val id: String,
  val symbol: String,
  val type: SignalDirection,
  val entryPrice: Double,
  val target1: Double,
  val target2: Double,
  val stopLoss: Double,
  val confluenceScore: Int,
  val timeAgo: String,
  val status: String,
  val riskReward: String,
  val rationale: String
)

data class NotificationLog(
  val id: String,
  val title: String,
  val description: String,
  val timestamp: String,
  val direction: SignalDirection,
  val isUnread: Boolean = true
)
