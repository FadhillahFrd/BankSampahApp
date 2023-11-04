package com.example.banksampah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.get
import com.example.banksampah.databinding.ActivityJemputSampahBinding
import com.example.banksampah.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JemputSampah : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityJemputSampahBinding
    private var hargaPerKG: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityJemputSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val items = listOf("Plastik", "Logam", "Kertas", "Duplex", "Minyak Jelantah")
        val adapter = ArrayAdapter(this, R.layout.list_item_dropdown, items)
        (binding.dropdownPosition.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        db = Firebase.database
        val dataRef = db.reference

        binding.btnCheckout.setOnClickListener {

            val nama = intent.getStringExtra("nama")
            val nohp = intent.getStringExtra("nohp")
            val username = intent.getStringExtra("username")
            val jenis = binding.dropdownPosition.editText?.text.toString()
            val berat = binding.inputBerat.text.toString()
            val tanggal = binding.inputTanggal.text.toString()
            val alamat = binding.inputAlamat.text.toString()
            val catatan = binding.inputTambahan.text.toString()

            when {
                jenis.isEmpty() -> {
                    binding.dropdownPosition.editText?.error = "Jenis Harus di isi!" }
                berat.isEmpty() -> {
                    binding.inputBerat.error = "Berat Harus di isi!"
                }
                tanggal.isEmpty() -> {
                    binding.inputTanggal.error = "Tanggal Harus di isi!"
                }
                alamat.isEmpty() -> {
                    binding.inputAlamat.error = "Alamat Harus di isi!"
                }
                catatan.isEmpty() -> {
                    binding.inputTambahan.error = "Catatan Harus di isi!"
                }
                else -> {
                    when (jenis) {
                        "Plastik" -> {
                            hargaPerKG = 5000
                        }
                        "Logam" -> {
                            hargaPerKG = 10000
                        }
                        "Kertas" -> {
                            hargaPerKG = 1700
                        }
                        "Duplex" -> {
                            hargaPerKG = 1500
                        }
                        "Minyak Jelantah" -> {
                            hargaPerKG = 4000
                        }
                    }

                    val harga = berat.toInt() * hargaPerKG
                    val dataSampah = DataSampah(nama, nohp, jenis, berat.toInt(), tanggal, alamat, catatan, harga)

                    binding.progressBar.visibility = View.VISIBLE
                    dataRef.child("data_user").child(username!!).push().setValue(dataSampah)
                    dataRef.child("data_admin").push().setValue(dataSampah)
                    binding.progressBar.visibility = View.INVISIBLE
                    finish()
                    Toast.makeText(this@JemputSampah, "Data berhasil di input!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}