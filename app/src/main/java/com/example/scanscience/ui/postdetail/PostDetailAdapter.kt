package com.example.scanscience.ui.postdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanscience.databinding.ItemObjectsBinding

class PostDetailAdapter(private val objectList: List<String>) : RecyclerView.Adapter<PostDetailAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: PostDetailAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: PostDetailAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemObjectsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return objectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(objectList[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(objectList[position])
        }
    }

    inner class ViewHolder(private val binding: ItemObjectsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(objects: String){
            binding.apply {
                btnObjectsDetail.text = objects
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }
}