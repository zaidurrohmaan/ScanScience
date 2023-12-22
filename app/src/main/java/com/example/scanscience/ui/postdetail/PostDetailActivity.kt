package com.example.scanscience.ui.postdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.scanscience.R
import com.example.scanscience.data.dummy.Post
import com.example.scanscience.databinding.ActivityPostDetailBinding
import com.example.scanscience.ui.home.PostAdapter
import com.example.scanscience.ui.resultfact.ResultDefinitionAdapter
import com.example.scanscience.ui.resultfact.ResultFactActivity

class PostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val imageUrl = intent.getStringExtra(EXTRA_IMAGE)
        val objects = intent.getStringArrayListExtra(EXTRA_OBJECTS) as ArrayList

        binding.apply {
            tvNameDetailPost.text = name
            Glide.with(this@PostDetailActivity)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(ivImageDetailPost)
        }

        val rvObjects = binding.rvObjectsDetailPost
        rvObjects.layoutManager = LinearLayoutManager(this)
        val adapter = PostDetailAdapter(objects)
        rvObjects.adapter = adapter

        adapter.setOnItemClickCallback(object : PostDetailAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                Intent(this@PostDetailActivity, ResultFactActivity::class.java).also {
                    it.putExtra(ResultFactActivity.EXTRA_OBJECT_NAME, data)
                    startActivity(it)
                }
            }
        })
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_OBJECTS = "extra_objects"
    }
}