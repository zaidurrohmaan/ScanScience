package com.example.scanscience.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.scanscience.R
import com.example.scanscience.data.dummy.Post
import com.example.scanscience.databinding.ActivityHomeBinding
import com.example.scanscience.databinding.ItemPostBinding
import org.w3c.dom.Text

class PostAdapter(private val postList: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(postList[position])
        }
    }

    inner class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post){

            binding.apply {
                Glide.with(itemView)
                    .load(post.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivImagePost)

                tvNamePost.text = post.userName
                tvLocationPost.text = post.location
                var objects = ""
                for(i in 0..<post.objects.size){
                    if(i!=0) objects += ", "
                    objects += post.objects[i]
                }
                tvObjectsPost.text = objects
                tvCaption.text = post.description
                tvDate.text = post.date
                tvCountObject.text = post.objectCount.toString()
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Post)
    }
}