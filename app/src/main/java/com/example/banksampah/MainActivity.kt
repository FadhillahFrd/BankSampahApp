package com.example.banksampah

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.banksampah.databinding.ActivityHalamanAdminBinding
import com.example.banksampah.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")

    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel
    private var accountFound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(LoginPref.getInstance(dataStore))
        )[LoginViewModel::class.java]

        db = Firebase.database

        binding.register.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
        binding.login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()

        when {
            username.isEmpty() -> {
                binding.username.error = "Username Harus Di isi!"
            }
            password.isEmpty() -> {
                binding.password.error = "Password Harus Di isi!"
            }
            else -> {
                binding.progressBar.visibility = View.VISIBLE
                db.reference.child("user")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            dataSnapshot.children.forEach {
                                val dataUsername = it.child("username").value

                                if (dataUsername == username) {
                                    binding.progressBar.visibility = View.INVISIBLE
                                    accountFound = true
                                    val dataPassword = it.child("password").value

                                    if (password == dataPassword) {
                                        val nama = it.child("nama").value.toString()
                                        val tipeUser = it.child("tipeUser").value.toString()
                                        val nohp = it.child("nohp").value.toString()

                                        loginViewModel.saveLogin(username, nama, tipeUser, nohp)

                                        if (tipeUser == "user") {
                                            startActivity(Intent(this@MainActivity, HalamanUser::class.java))
                                            finish()
                                        } else {
                                            startActivity(Intent(this@MainActivity, HalamanAdmin::class.java))
                                            finish()
                                        }
                                        return
                                    }
                                    Toast.makeText(this@MainActivity, "Password salah!", Toast.LENGTH_LONG).show()
                                    return
                                } else {
                                    accountFound = false
                                }
                            }

                            if (!accountFound) {
                                binding.progressBar.visibility = View.INVISIBLE
                                Toast.makeText(this@MainActivity, "User tidak ditemukan, harap daftar!", Toast.LENGTH_LONG).show()
                            }

                        }

                        override fun onCancelled(error: DatabaseError) {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(
                                this@MainActivity,
                                "error: $error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }
    }
}