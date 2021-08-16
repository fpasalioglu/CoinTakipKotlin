package com.furkanpasalioglu.coinyeni.ui.ekle.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.furkanpasalioglu.coinyeni.data.database.dao.CoinsDao
import com.furkanpasalioglu.coinyeni.databinding.ActivityEkleBinding
import com.furkanpasalioglu.coinyeni.data.database.AppDatabase
import com.furkanpasalioglu.coinyeni.data.model.DatabaseCoin

class EkleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEkleBinding

    private var coinsDao : CoinsDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Coin Ekle"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val db = AppDatabase.getDatabase(application)
        coinsDao = db?.coinsDao()

        binding.ekleButton.setOnClickListener {
            if (binding.adText.equals("") || binding.miktarText.equals("") || binding.alisText.equals("")) {
                Toast.makeText(applicationContext, "Tüm Bilgileri Eksiksiz Doldurunuz", Toast.LENGTH_SHORT).show()
            }else{
                var tip = "BTC"
                if (binding.tipSwitch.isChecked) tip = "TRY"

                val coin = DatabaseCoin(0,binding.adText.text.toString()+tip,binding.miktarText.text.toString()
                ,binding.alisText.text.toString(),"")
                coinsDao?.insert(coin)

                Toast.makeText(applicationContext, "Coin eklendi!", Toast.LENGTH_SHORT).show()
            }
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