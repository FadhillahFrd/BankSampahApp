package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.banksampah.databinding.ActivityTukarJelantahBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TukarJelantah : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityTukarJelantahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityTukarJelantahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        db = Firebase.database
        val dataRef = db.reference

        binding.btnTukar.setOnClickListener {

            val nama = intent.getStringExtra("nama")
            val nohp = intent.getStringExtra("nohp")
            val username = intent.getStringExtra("username")
            val liter = binding.inputNetto.text.toString()
            val tanggal = binding.inputTanggal.text.toString()
            val alamat = binding.inputAlamat.text.toString()
            val catatan = binding.inputTambahan.text.toString()

            when {
                liter.isEmpty() -> {
                    binding.inputNetto.error = "Netto Harus di isi1"
                }
                tanggal.isEmpty() -> {
                    binding.inputTanggal.error = "Tanggal Harus di isi1"
                }
                alamat.isEmpty() -> {
                    binding.inputAlamat.error = "Alamat Harus di isi1"
                }
                catatan.isEmpty() -> {
                    binding.inputTambahan.error = "Catatan Harus di isi1"
                }
                else -> {
                    val jenis = "Minyak Jelantah"
                    val hargaPerLiter = 4000

                    val harga = liter.toInt() * hargaPerLiter
                    val dataSampah = DataSampah(nama, nohp, jenis, liter.toInt(), tanggal, alamat, catatan, harga)

                    binding.progressBar.visibility = View.VISIBLE
                    dataRef.child("data_user").child(username!!).push().setValue(dataSampah)
                    dataRef.child("data_admin").push().setValue(dataSampah)
                    binding.progressBar.visibility = View.INVISIBLE
                    finish()
                    Toast.makeText(this@TukarJelantah, "Data berhasil di input!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}