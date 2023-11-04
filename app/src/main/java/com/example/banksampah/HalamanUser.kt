package com.example.banksampah

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.banksampah.databinding.ActivityHalamanAdminBinding
import com.example.banksampah.databinding.ActivityHalamanUserBinding

class HalamanUser : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")

    private lateinit var binding: ActivityHalamanUserBinding
    private lateinit var loginViewModel: LoginViewModel
    private var nama: String? = null
    private var nohp: String? = null
    private var username: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_user)

        supportActionBar?.hide()

        binding = ActivityHalamanUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPref.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) {
            if (!it.username.isEmpty()) {
                if (it.tipeUser == "admin") {
                    startActivity(Intent(this, HalamanAdmin::class.java))
                    finish()
                }
                nama = it.nama
                nohp = it.noHp
                username = it.username
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.contentMain.cvInput.setOnClickListener {
            val intent = Intent(this, JemputSampah::class.java)
            intent.putExtra("nama", nama)
            intent.putExtra("nohp", nohp)
            intent.putExtra("username", username)
            startActivity(intent)
        }
        binding.contentMain.cvJelantah.setOnClickListener {
            val intent = Intent(this, TukarJelantah::class.java)
            intent.putExtra("nama", nama)
            intent.putExtra("nohp", nohp)
            intent.putExtra("username", username)
            startActivity(intent)
        }
        binding.contentMain.cvKategori.setOnClickListener {
            startActivity(Intent(this, KategoriSampah::class.java))
        }
        binding.contentMain.cvHistory.setOnClickListener {
            val intent = Intent(this, Riwayat::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            loginViewModel.logout()
        }
    }
}