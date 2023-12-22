package com.example.scanscience.ui.resultfact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanscience.data.response.ItemDeskripsiUmum
import com.example.scanscience.data.response.ItemFactResponse
import com.example.scanscience.data.response.ItemFakta
import com.example.scanscience.databinding.ItemDeskripsiBinding

class ResultFactAdapter(private val factList: List<ItemFakta>) : RecyclerView.Adapter<ResultFactAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDeskripsiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return factList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(factList[position])
    }

    inner class ViewHolder(private val binding: ItemDeskripsiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fakta: ItemFakta){
            binding.apply {
                tvSubHeader.text = fakta.judul
                tvBody.text = fakta.isi
            }
        }
    }
}