package com.furkanpasalioglu.coinyeni.data.api

import com.furkanpasalioglu.coinyeni.data.model.BinanceResponseItem
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getCoins(): Response<List<BinanceResponseItem>> = apiService.getCoins()
    override suspend fun getCoinBySymbol(symbol : String): BinanceResponseItem = apiService.getCoinsBySymbol(symbol)
}