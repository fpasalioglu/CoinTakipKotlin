package com.furkanpasalioglu.coinyeni.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkanpasalioglu.coinyeni.data.database.dao.CoinsDao
import com.furkanpasalioglu.coinyeni.data.model.DatabaseCoin

@Database(entities = [DatabaseCoin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "coins"
                        ).allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE
        }
    }
}