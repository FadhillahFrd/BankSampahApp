package com.example.banksampah

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ListRiwayat (
    val tanggal: String? = null,
    val nama: String? = null,
    val nohp: String? = null,
    val alamat: String? = null,
    val catatan: String? = null,
    val berat: Int? = null,
    val harga: Int? = null,
    val jenis: String? = null,
) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
