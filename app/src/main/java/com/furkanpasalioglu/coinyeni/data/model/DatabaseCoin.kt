package com.furkanpasalioglu.coinyeni.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class DatabaseCoin(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val symbol: String,
    val miktar: String,
    val alis: String,
    var lastPrice: String
){
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