package com.example.banksampah

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banksampah.databinding.ActivityRiwayatBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Riwayat : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var riwayatAdapter: RiwayatAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setBackgroundDrawable(getColor(R.color.white).toDrawable())
            title = "Riwayat Penjualan"
            setDisplayHomeAsUpEnabled(true)
        }

        db = Firebase.database
        val username = intent.getStringExtra("username")
        val dataRef = db.reference.child("data_user").child(username!!)

        binding.rvListRiwayat.layoutManager = LinearLayoutManager(this)

        val optionsRiwayat = FirebaseRecyclerOptions.Builder<ListRiwayat>()
            .setQuery(dataRef, ListRiwayat::class.java)
            .build()
        riwayatAdapter = RiwayatAdapter(optionsRiwayat)
        binding.rvListRiwayat.adapter = riwayatAdapter

        riwayatAdapter.startListening()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        riwayatAdapter.stopListening()
    }

}