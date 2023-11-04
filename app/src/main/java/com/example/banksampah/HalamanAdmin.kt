package com.example.banksampah

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banksampah.databinding.ActivityHalamanAdminBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HalamanAdmin : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityHalamanAdminBinding
    private lateinit var halamanAdminAdapter: HalamanAdminAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityHalamanAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference.child("data_admin")

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPref.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) {
            if (!it.username.isEmpty()) {
                if (it.tipeUser == "user") {
                    startActivity(Intent(this, HalamanUser::class.java))
                    finish()
                }
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.rvListRiwayat.layoutManager = LinearLayoutManager(this)

        val optionsRiwayat = FirebaseRecyclerOptions.Builder<ListRiwayat>()
            .setQuery(database, ListRiwayat::class.java)
            .build()
        halamanAdminAdapter = HalamanAdminAdapter(optionsRiwayat, this)
        binding.rvListRiwayat.adapter = halamanAdminAdapter

        halamanAdminAdapter.startListening()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                loginViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        halamanAdminAdapter.stopListening()
    }
}