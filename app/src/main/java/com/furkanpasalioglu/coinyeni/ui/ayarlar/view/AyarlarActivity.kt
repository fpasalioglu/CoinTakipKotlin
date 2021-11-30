package com.furkanpasalioglu.coinyeni.ui.ayarlar.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.furkanpasalioglu.coinyeni.data.model.Ayar
import com.furkanpasalioglu.coinyeni.databinding.ActivityAyarlarBinding
import com.furkanpasalioglu.coinyeni.ui.main.view.MainActivity.Companion.ayar
import com.furkanpasalioglu.coinyeni.ui.main.view.MainActivity.Companion.database

class AyarlarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAyarlarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAyarlarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val btc = ayar.btc
        val tl = ayar.tl

        binding.btcmiktarEdit.setText(btc.toString())
        binding.tlmiktarText.setText(tl.toString())

        binding.button.setOnClickListener {
            val ayarlar = Ayar(binding.btcmiktarEdit.text.toString().toFloat(), binding.tlmiktarText.text.toString().toFloat())
            database.child("ayarlar").setValue(ayarlar)
            Toast.makeText(applicationContext,"Kayıt edildi",Toast.LENGTH_LONG).show()
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