package com.example.banksampah

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banksampah.databinding.ItemRiwayatAdminBinding
import com.example.banksampah.databinding.ItemRiwayatUserBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class RiwayatAdapter(options: FirebaseRecyclerOptions<ListRiwayat>) :
    FirebaseRecyclerAdapter<ListRiwayat, RiwayatAdapter.ListRiwayatViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRiwayatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_riwayat_user, parent, false)
        val binding = ItemRiwayatUserBinding.bind(view)
        return ListRiwayatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRiwayatViewHolder, position: Int, model: ListRiwayat) {
        holder.bind(model)
    }

    inner class ListRiwayatViewHolder(private val binding: ItemRiwayatUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ListRiwayat) {
            binding.tvTanggal.text = "${item.tanggal}"
            binding.tvNama.text = "${item.nama}"
            binding.tvAlamat.text = "${item.alamat}"
            binding.tvJenis.text = "Kategori Sampah: ${item.jenis}"

            if (item.jenis == "Minyak Jelantah") {
                binding.tvHarga.text = "Rp ${item.harga} (${item.berat} liter)"
            } else {
                binding.tvHarga.text = "Rp ${item.harga} (${item.berat} kg)"
            }
        }
    }
}