package com.furkanpasalioglu.coinyeni.data.model

import java.io.Serializable

data class DatabaseCoin(
    val symbol: String = "",
    val miktar: String = "",
    val alis: String = "",
    var lastPrice: String = ""
) : Serializable {
    fun getYuzdeKar(lastPrice: String): String {
        val deger: Float = miktar.toFloat() * lastPrice.toFloat()
        val verilen: Float = miktar.toFloat() * alis.toFloat()
        return "% " + String.format("%.2f", (deger - verilen) * 100 / verilen)
    }

    fun getKar(lastPrice: String): Float {
        val deger: Float = miktar.toFloat() * lastPrice.toFloat()
        val verilen: Float = miktar.toFloat() * alis.toFloat()
        return deger - verilen
    }

    fun getDeger(lastPrice: String): Float {
        return miktar.toFloat() * lastPrice.toFloat()
    }
}