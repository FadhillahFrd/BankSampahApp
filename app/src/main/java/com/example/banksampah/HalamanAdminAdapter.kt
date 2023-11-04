package com.example.banksampah

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.databinding.ItemRiwayatAdminBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HalamanAdminAdapter(options: FirebaseRecyclerOptions<ListRiwayat>, context: Context) :
    FirebaseRecyclerAdapter<ListRiwayat, HalamanAdminAdapter.ListRiwayatViewHolder>(options) {

    private val activityHalamanAdmin = (context as HalamanAdmin)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRiwayatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_riwayat_admin, parent, false)
        val binding = ItemRiwayatAdminBinding.bind(view)
        return ListRiwayatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRiwayatViewHolder, position: Int, model: ListRiwayat) {
        holder.bind(model)
    }

    inner class ListRiwayatViewHolder(private val binding: ItemRiwayatAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ListRiwayat) {
            binding.tvTanggal.text = "${item.tanggal}"
            binding.tvNama.text = "${item.nama} - ${item.nohp}"
            binding.tvAlamat.text = "${item.alamat}, note : ${item.catatan}"
            binding.tvJenis.text = "Kategori Sampah: ${item.jenis}"

            if (item.jenis == "Minyak Jelantah") {
                binding.tvHarga.text = "Rp ${item.harga} (${item.berat} liter)"
            } else {
                binding.tvHarga.text = "Rp ${item.harga} (${item.berat} kg)"
            }

            binding.ButtonChat.setOnClickListener {
                val directLink = "https://wa.me/${item.nohp}"
                val uri = Uri.parse(directLink)
                val directToWhatsApp = Intent(Intent.ACTION_VIEW, uri)
                activityHalamanAdmin.startActivity(directToWhatsApp)
            }
        }
    }
}