package com.example.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {
  private const val BINANCE_BASE_URL = "https://api.binance.com/"
  private const val COINGECKO_BASE_URL = "https://api.coingecko.com/"

  private val moshi: Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

  private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .writeTimeout(10, TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .build()

  val apiService: BinanceApiService by lazy {
    Retrofit.Builder()
      .baseUrl(BINANCE_BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
      .create(BinanceApiService::class.java)
  }

  val coinGeckoApiService: CoinGeckoApiService by lazy {
    Retrofit.Builder()
      .baseUrl(COINGECKO_BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .build()
      .create(CoinGeckoApiService::class.java)
  }
}
