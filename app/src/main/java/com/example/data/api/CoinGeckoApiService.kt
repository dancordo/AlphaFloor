package com.example.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApiService {
  @GET("api/v3/coins/markets")
  suspend fun getCoinsMarkets(
    @Query("vs_currency") vsCurrency: String = "usd",
    @Query("ids") ids: String = "bitcoin,ethereum,solana,binancecoin,avalanche-2,ripple,dogecoin,cardano",
    @Query("order") order: String = "market_cap_desc",
    @Query("sparkline") sparkline: Boolean = true,
    @Query("price_change_percentage") priceChangePercentage: String = "24h"
  ): List<CoinGeckoCoinDto>
}
