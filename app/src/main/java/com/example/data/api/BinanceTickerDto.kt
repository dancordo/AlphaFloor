package com.example.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BinanceTickerDto(
  @Json(name = "symbol") val symbol: String,
  @Json(name = "lastPrice") val lastPrice: String,
  @Json(name = "priceChangePercent") val priceChangePercent: String,
  @Json(name = "highPrice") val highPrice: String,
  @Json(name = "lowPrice") val lowPrice: String,
  @Json(name = "volume") val volume: String,
  @Json(name = "quoteVolume") val quoteVolume: String,
  @Json(name = "openPrice") val openPrice: String? = null
)
