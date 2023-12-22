package com.example.scanscience.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanscience.R
import com.example.scanscience.data.dummy.Post
import com.example.scanscience.databinding.ActivityHomeBinding
import com.example.scanscience.ui.profile.ProfileActivity
import com.example.scanscience.ui.camera.CameraActivity
import com.example.scanscience.ui.explore.ExploreActivity
import com.example.scanscience.ui.postdetail.PostDetailActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            startActivity(Intent(this@HomeActivity, CameraActivity::class.java))
            finish()
        }

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
            finish()
        }

        binding.btnExplore.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ExploreActivity::class.java))
        }

        val recyclerView = binding.rvPost
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PostAdapter(dummyPost)
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : PostAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Post) {
                Intent(this@HomeActivity, PostDetailActivity::class.java).also {
                    it.putExtra(PostDetailActivity.EXTRA_NAME, data.userName)
                    it.putExtra(PostDetailActivity.EXTRA_IMAGE, data.photoUrl)
                    it.putExtra(PostDetailActivity.EXTRA_OBJECTS, data.objects)
                    startActivity(it)
                }
            }
        })
    }

    var doubleBackToExitOnce:Boolean = false

    override fun onBackPressed() {
        if(doubleBackToExitOnce){
            super.onBackPressed()
            return
        }

        this.doubleBackToExitOnce = true

        //displays a toast message when user clicks exit button
        Toast.makeText(this@HomeActivity, getString(R.string.tekan_lagi_untuk_keluar_aplikasi), Toast.LENGTH_SHORT).show()

        //displays the toast message for a while
        Handler().postDelayed({
            kotlin.run { doubleBackToExitOnce = false }
        }, 2000)
    }

    var dummyPost = listOf<Post>(
        Post(
            "Zaidurrohman",
            2,
            "https://wallpapers.com/images/hd/lion-chasing-zebra-ufm03etbq5jqiekz.jpg",
            "Sungguh pengalaman yang sangat menggembirakan sekaligus menegangkan melihat dua mamalia ini berkejaran",
            "Indonesia",
            "22 Desember 2023",
            arrayListOf("Singa", "Zebra")
        ),
        Post(
            "Ester Gracia",
            1,
            "https://cdn.shortpixel.ai/spai/q_glossy+w_1082+to_auto+ret_img/www.fauna-flora.org/wp-content/uploads/2023/05/rhino_shutterstock_60362779-scaled-e1655220982564.jpg",
            "Pertama kali saya melihat binatang bercula ini secara langsung :)",
            "Indonesia",
            "22 Desember 2023",
            arrayListOf("Badak")
        ),
        Post(
            "Andi Budiman",
            1,
            "https://cdn.linkumkm.id/library/1/0/6/3/4/1/106341_840x576.jpg",
            "Sangat menggemaskan. Saya jadi ingin memelihara kambing di rumah",
            "Indonesia",
            "21 Desember 2023",
            arrayListOf("Kambing")
        ),
        Post(
            "John Doe",
            1,
            "https://vetmed.tamu.edu/news/wp-content/uploads/sites/9/2022/05/bison1-AdobeStock_187916675.jpeg",
            "Binatang ini begitu besar. Saya merasa takut saat berada dekat dengannya",
            "Indonesia",
            "20 Desember 2023",
            arrayListOf("Bison")
        ),
        Post(
            "Darwis Andrew",
            1,
            "https://agrozine.id/wp-content/uploads/2023/01/Burung-Pelatuk.jpg",
            "Masih ingat dengan film kartun Woody Woodpecker? Inilah Woody sekarang, sudah berkeluarga dan hidup bahagia :)",
            "Indonesia",
            "20 Desember 2023",
            arrayListOf("Burung Pelatuk")
        ),
    )
}