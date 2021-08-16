package com.furkanpasalioglu.coinyeni.data.api

import com.furkanpasalioglu.coinyeni.data.model.BinanceResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("24hr")
    suspend fun getCoins(): Response<List<BinanceResponseItem>>

    @GET("24hr?")
    suspend fun getCoinsBySymbol(
        @Query("symbol") symbol: String
    ): BinanceResponseItem

}