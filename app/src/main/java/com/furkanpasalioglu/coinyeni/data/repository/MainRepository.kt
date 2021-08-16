package com.furkanpasalioglu.coinyeni.data.repository

import com.furkanpasalioglu.coinyeni.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getCoins() =  apiHelper.getCoins()

}