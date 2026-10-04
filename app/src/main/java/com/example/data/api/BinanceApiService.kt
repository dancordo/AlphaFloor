package com.example.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceApiService {
  @GET("api/v3/ticker/24hr")
  suspend fun get24hTickers(
    @Query("symbols") symbols: String
  ): List<BinanceTickerDto>

  @GET("api/v3/klines")
  suspend fun getKlines(
    @Query("symbol") symbol: String,
    @Query("interval") interval: String = "1h",
    @Query("limit") limit: Int = 20
  ): List<List<Any>>
}
