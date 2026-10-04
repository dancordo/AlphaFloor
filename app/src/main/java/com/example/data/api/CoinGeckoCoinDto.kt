package com.example.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinGeckoCoinDto(
  @Json(name = "id") val id: String,
  @Json(name = "symbol") val symbol: String,
  @Json(name = "name") val name: String,
  @Json(name = "image") val image: String? = null,
  @Json(name = "current_price") val currentPrice: Double? = null,
  @Json(name = "high_24h") val high24h: Double? = null,
  @Json(name = "low_24h") val low24h: Double? = null,
  @Json(name = "price_change_percentage_24h") val priceChangePercentage24h: Double? = null,
  @Json(name = "total_volume") val totalVolume: Double? = null,
  @Json(name = "market_cap") val marketCap: Double? = null,
  @Json(name = "market_cap_rank") val marketCapRank: Int? = null,
  @Json(name = "sparkline_in_7d") val sparklineIn7d: SparklineDataDto? = null
)

@JsonClass(generateAdapter = true)
data class SparklineDataDto(
  @Json(name = "price") val price: List<Double>? = null
)
