package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.banksampah.databinding.ActivityRegisterBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        db = Firebase.database

        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.register.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val nama = binding.nama.text.toString()
        val username = binding.username.text.toString()
        val nohp = binding.nohp.text.toString()
        val password = binding.password.text.toString()

        when {
            nama.isEmpty() -> {
                binding.nama.error = "Nama Harus Di isi!"
            }
            username.isEmpty() -> {
                binding.username.error = "Username Harus Di isi!"
            }
            nohp.isEmpty() -> {
                binding.nohp.error = "Nomor Handphone Harus Di isi!"
            }
            password.isEmpty() -> {
                binding.password.error = "Password Harus Di isi!"
            }
            else -> {
                val dataUser = DataUser(nama, tipeUser = "user", username, nohp, password)
                binding.progressBar.visibility = View.INVISIBLE
                db.reference.child("user").child(username).setValue(dataUser)

                Toast.makeText(this, "User berhasil ditambahkan, silahkan login", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}