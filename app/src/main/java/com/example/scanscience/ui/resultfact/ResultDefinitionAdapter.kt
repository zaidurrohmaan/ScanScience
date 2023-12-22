package com.example.scanscience.ui.resultfact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanscience.data.response.ItemDeskripsiUmum
import com.example.scanscience.databinding.ItemDeskripsiBinding

class ResultDefinitionAdapter(private val descriptionList: List<ItemDeskripsiUmum>) : RecyclerView.Adapter<ResultDefinitionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDeskripsiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return descriptionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(descriptionList[position])
    }

    inner class ViewHolder(private val binding: ItemDeskripsiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(deskripsi: ItemDeskripsiUmum){
            binding.apply {
                tvSubHeader.text = deskripsi.judul
                tvBody.text = deskripsi.isi
            }
        }
    }
}