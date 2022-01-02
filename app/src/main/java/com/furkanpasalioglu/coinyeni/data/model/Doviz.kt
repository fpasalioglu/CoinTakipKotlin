package com.furkanpasalioglu.coinyeni.data.model


import com.google.gson.annotations.SerializedName

data class Doviz(
    @SerializedName("USD")
    val usd: USD
)