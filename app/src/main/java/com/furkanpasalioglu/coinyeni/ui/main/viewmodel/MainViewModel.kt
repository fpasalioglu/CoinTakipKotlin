package com.furkanpasalioglu.coinyeni.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.furkanpasalioglu.coinyeni.data.database.AppDatabase
import com.furkanpasalioglu.coinyeni.data.database.dao.CoinsDao
import com.furkanpasalioglu.coinyeni.data.model.BinanceResponseItem
import com.furkanpasalioglu.coinyeni.data.repository.MainRepository
import com.furkanpasalioglu.coinyeni.utils.NetworkHelper
import com.furkanpasalioglu.coinyeni.data.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application,
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    var coinsDao : CoinsDao? = null
    private val _coins = MutableLiveData<Resource<List<BinanceResponseItem>>>()
    val coins: LiveData<Resource<List<BinanceResponseItem>>>
        get() = _coins

    init {
        fetchCoins()
        val db = AppDatabase.getDatabase(application)
        coinsDao = db?.coinsDao()
    }

    fun fetchCoins() {
        viewModelScope.launch {
            _coins.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getCoins().let {
                    if (it.isSuccessful) {
                        _coins.postValue(Resource.success(it.body()))
                    } else _coins.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _coins.postValue(Resource.error("No internet connection", null))
        }
    }
}