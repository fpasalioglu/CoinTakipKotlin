package com.furkanpasalioglu.coinyeni.ui.main.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkanpasalioglu.coinyeni.R
import com.furkanpasalioglu.coinyeni.data.database.dao.CoinsDao
import com.furkanpasalioglu.coinyeni.data.model.BinanceResponseItem
import com.furkanpasalioglu.coinyeni.databinding.ActivityMainBinding
import com.furkanpasalioglu.coinyeni.ui.main.adapter.MainAdapter
import com.furkanpasalioglu.coinyeni.ui.main.viewmodel.MainViewModel
import com.furkanpasalioglu.coinyeni.data.model.Status
import com.furkanpasalioglu.coinyeni.data.database.AppDatabase
import com.furkanpasalioglu.coinyeni.data.model.DatabaseCoin
import com.furkanpasalioglu.coinyeni.ui.ayarlar.view.AyarlarActivity
import com.furkanpasalioglu.coinyeni.ui.ekle.view.EkleActivity
import com.furkanpasalioglu.coinyeni.ui.goster.view.GosterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var binding : ActivityMainBinding
    private val list:ArrayList<DatabaseCoin> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf()){ position -> onListItemClick(position) }
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.coins.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let {
                        coins -> renderList(coins)
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun renderList(coins: List<BinanceResponseItem>) {
        list.clear()
        val databaseCoins : List<DatabaseCoin>? = mainViewModel.coinsDao?.getAllCoins()
        if (databaseCoins?.isNotEmpty()!!) {
            for (element in databaseCoins) {
                coins.forEach {
                    if (it.symbol == "BTCTRY"){
                        anlikBTC = it.lastPrice.split(".")[0]
                        binding.anlikbtc.text = "$anlikBTC TL"
                    }

                    if (element.symbol == it.symbol) {
                        element.lastPrice = it.lastPrice
                        list.add(element)
                    }
                }
            }
            //todo settings
            var portBtc = 0F
            var zararToplam = 0F
            list.forEach{
                if (it.symbol.contains("BTC")) {
                    portBtc += it.getDeger(it.lastPrice)
                    zararToplam += it.getKar(it.lastPrice)
                } else {
                    portBtc += it.getDeger(it.lastPrice) / anlikBTC.toFloat()
                    zararToplam += it.getKar(it.lastPrice) / anlikBTC.toFloat()
                }
            }

            val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            portBtc += preferences.getFloat("btc",0F)
            var portTl = portBtc * anlikBTC.toFloat()
            portTl+=preferences.getFloat("tl",0F)

            val zararTl = zararToplam * anlikBTC.toFloat()
            binding.portBtc.text = "$portBtc BTC"
            binding.zarardurumtextview.text = "$zararTl TL"
            binding.portTl.text = "$portTl TL"
        }
        adapter.addData(list)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        mainViewModel.fetchCoins()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ekle -> {
                val intent = Intent(this, EkleActivity::class.java)
                resultLauncher.launch(intent)
                true
            }
            R.id.yenile -> {
                mainViewModel.fetchCoins()
                true
            }
            R.id.ayarlar -> {
                val intent = Intent(this, AyarlarActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onListItemClick(position: Int) {
        val intent = Intent(this, GosterActivity::class.java)
        intent.putExtra("symbol",list[position].symbol)
        resultLauncher.launch(intent)
    }

    companion object{
        lateinit var anlikBTC : String
    }
}