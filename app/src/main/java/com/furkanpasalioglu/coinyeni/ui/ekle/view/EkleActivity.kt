package com.furkanpasalioglu.coinyeni.ui.ekle.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.furkanpasalioglu.coinyeni.databinding.ActivityEkleBinding
import com.furkanpasalioglu.coinyeni.data.model.DatabaseCoin
import com.furkanpasalioglu.coinyeni.ui.main.view.MainActivity.Companion.database

class EkleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEkleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Coin Ekle"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        binding.ekleButton.setOnClickListener {
            if (binding.adText.equals("") || binding.miktarText.equals("") || binding.alisText.equals("")) {
                Toast.makeText(applicationContext, "Tüm Bilgileri Eksiksiz Doldurunuz", Toast.LENGTH_SHORT).show()
            }else{
                var note = ""
                if(binding.noteTxt.text.toString() != "")
                    note = binding.noteTxt.text.toString()
                var tip = "BTC"
                if (binding.tipSwitch.isChecked) tip = "TRY"

                val coin = DatabaseCoin(binding.adText.text.toString()+tip, binding.miktarText.text.toString()
                , binding.alisText.text.toString(),"", note)

                database.child("koinler").push().setValue(coin)

                Toast.makeText(applicationContext, "Coin eklendi!", Toast.LENGTH_SHORT).show()

                binding.adText.setText("")
                binding.miktarText.setText("")
                binding.alisText.setText("")
                binding.noteTxt.setText("")
                onBackPressed()
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