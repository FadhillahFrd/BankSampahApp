package com.example.banksampah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banksampah.databinding.ActivityJemputSampahBinding
import com.example.banksampah.databinding.ActivityKategoriSampahBinding

class KategoriSampah : AppCompatActivity() {

    private lateinit var binding: ActivityKategoriSampahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKategoriSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}