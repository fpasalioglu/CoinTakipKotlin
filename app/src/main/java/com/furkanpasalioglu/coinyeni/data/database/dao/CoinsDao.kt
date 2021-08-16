package com.furkanpasalioglu.coinyeni.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.furkanpasalioglu.coinyeni.data.model.BinanceResponseItem
import com.furkanpasalioglu.coinyeni.data.model.DatabaseCoin

@Dao
interface CoinsDao {

    @Query("SELECT * FROM coins")
    fun getAllCoins(): List<DatabaseCoin>

    @Query("SELECT * FROM coins where symbol == :symbol")
    fun isInCoins(symbol:String): Boolean

    @Query("SELECT * FROM coins where symbol == :symbol")
    fun getCoinBySymbol(symbol:String): DatabaseCoin

    @Query("DELETE FROM coins where symbol == :symbol")
    fun deleteBySymbolId(symbol : String): Void

    @Insert
    fun insert(databaseCoin: DatabaseCoin)

    @Delete
    fun delete(databaseCoin: DatabaseCoin)
}