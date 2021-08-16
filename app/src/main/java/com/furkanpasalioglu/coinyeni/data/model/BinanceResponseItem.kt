package com.furkanpasalioglu.coinyeni.data.model

import com.google.gson.annotations.SerializedName

data class BinanceResponseItem(
    @SerializedName("highPrice")
    val highPrice: String,
    @SerializedName("lastPrice")
    val lastPrice: String,
    @SerializedName("lowPrice")
    val lowPrice: String,
    @SerializedName("prevClosePrice")
    val prevClosePrice: String,
    @SerializedName("priceChange")
    val priceChange: String,
    @SerializedName("priceChangePercent")
    val priceChangePercent: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("quoteVolume")
    val quoteVolume: String
)