package com.furkanpasalioglu.coinyeni.ui.goster.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.furkanpasalioglu.coinyeni.data.api.ApiHelper
import com.furkanpasalioglu.coinyeni.data.database.AppDatabase
import com.furkanpasalioglu.coinyeni.data.database.dao.CoinsDao
import com.furkanpasalioglu.coinyeni.data.model.BinanceResponseItem
import com.furkanpasalioglu.coinyeni.data.model.DatabaseCoin
import com.furkanpasalioglu.coinyeni.databinding.ActivityGosterBinding
import com.furkanpasalioglu.coinyeni.ui.main.view.MainActivity.Companion.anlikBTC
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GosterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGosterBinding
    private lateinit var coin : DatabaseCoin
    var coinsDao : CoinsDao? = null
    private lateinit var binanceResponseItem : BinanceResponseItem

    @Inject
    lateinit var apiHelper: ApiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGosterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val db = AppDatabase.getDatabase(application)
        coinsDao = db?.coinsDao()

        coin = intent.getStringExtra("symbol")?.let { coinsDao?.getCoinBySymbol(it) }!!
        title = "Coin Detayları"

        lifecycleScope.launch {
            binanceResponseItem = apiHelper.getCoinBySymbol(coin.symbol)

            binding.hacim.text = binanceResponseItem.quoteVolume
            binding.endusuk.text = binanceResponseItem.lowPrice
            binding.enyuksek.text = binanceResponseItem.highPrice
            binding.fiyati.text = binanceResponseItem.lastPrice
            binding.degeri.text = (coin.miktar.toFloat() * binanceResponseItem.lastPrice.toFloat()).toString()
            binding.karzarar.text = coin.getKar(binanceResponseItem.lastPrice).toString()
            binding.yuzde.text = coin.getYuzdeKar(binanceResponseItem.lastPrice)
            if (coin.symbol.contains("TRY")){
                binding.tllinear.visibility = View.GONE
                binding.text1.text = "TL"
                binding.text2.text = "TL"
                binding.text3.text = "TL"
                binding.text4.text = "TL"
                binding.text5.text = "TL"
                binding.text6.text = "TL"
                binding.text7.text = "TL"
            }else{
                binding.tldeg.text = (coin.getKar(binanceResponseItem.lastPrice)*anlikBTC.toFloat()).toString()
            }
        }

        binding.adi.text = coin.symbol
        binding.miktar.text = coin.miktar
        binding.alisfiyati.text = coin.alis

        binding.button2.setOnClickListener {
            coinsDao?.delete(coin)
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}