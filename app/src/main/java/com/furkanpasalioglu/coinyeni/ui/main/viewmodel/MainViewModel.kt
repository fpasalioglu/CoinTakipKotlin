package com.furkanpasalioglu.coinyeni.ui.main.viewmodel

import androidx.lifecycle.*
import com.furkanpasalioglu.coinyeni.data.model.BinanceResponseItem
import com.furkanpasalioglu.coinyeni.data.repository.MainRepository
import com.furkanpasalioglu.coinyeni.utils.NetworkHelper
import com.furkanpasalioglu.coinyeni.data.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _coins = MutableLiveData<Resource<List<BinanceResponseItem>>>()
    val coins: LiveData<Resource<List<BinanceResponseItem>>>
        get() = _coins

    init {
        fetchCoins()
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