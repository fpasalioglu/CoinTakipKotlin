package com.furkanpasalioglu.coinyeni.data.api

import com.furkanpasalioglu.coinyeni.data.model.BinanceResponseItem
import retrofit2.Response

interface ApiHelper {

    suspend fun getCoins(): Response<List<BinanceResponseItem>>

    suspend fun getCoinBySymbol(symbol : String): BinanceResponseItem
}