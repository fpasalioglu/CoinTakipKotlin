package com.furkanpasalioglu.coinyeni.ui.ayarlar.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.furkanpasalioglu.coinyeni.data.database.AppDatabase
import com.furkanpasalioglu.coinyeni.databinding.ActivityAyarlarBinding
import com.furkanpasalioglu.coinyeni.ui.main.view.MainActivity

class AyarlarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAyarlarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAyarlarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val db = AppDatabase.getDatabase(application)!!
        binding.export.setText(db.coinsDao().getAllCoins().toString())

        val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val edit = preferences.edit()
        val btc = preferences.getFloat("btc",0F)
        val tl = preferences.getFloat("tl",0F)

        binding.btcmiktarEdit.setText(btc.toString())
        binding.tlmiktarText.setText(tl.toString())

        binding.button.setOnClickListener {
            edit.putFloat("btc",binding.btcmiktarEdit.text.toString().toFloat())
            edit.putFloat("tl",binding.tlmiktarText.text.toString().toFloat())
            edit.apply()
            Toast.makeText(applicationContext,"Kayıt edildi",Toast.LENGTH_SHORT).show()
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