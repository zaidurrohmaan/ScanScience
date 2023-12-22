package com.example.scanscience.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.scanscience.R
import com.example.scanscience.databinding.ActivityProfileBinding
import com.example.scanscience.ui.camera.CameraActivity
import com.example.scanscience.ui.home.HomeActivity
import com.example.scanscience.ui.login.LoginActivity
import com.example.scanscience.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: ProfileViewModel by viewModels {
            factory
        }

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnCamera.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, CameraActivity::class.java))
            finish()
        }

        binding.btnLogout.setOnClickListener {
            showAlertDialog()
        }

        viewModel.session.observe(this) {session ->
            if(!session) {
                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                finish()
            }
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    var doubleBackToExitOnce:Boolean = false

    override fun onBackPressed() {
        if(doubleBackToExitOnce){
            super.onBackPressed()
            return
        }

        this.doubleBackToExitOnce = true

        //displays a toast message when user clicks exit button
        Toast.makeText(this@ProfileActivity, getString(R.string.tekan_lagi_untuk_keluar_aplikasi), Toast.LENGTH_SHORT).show()

        //displays the toast message for a while
        Handler().postDelayed({
            kotlin.run { doubleBackToExitOnce = false }
        }, 2000)
    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this@ProfileActivity)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: ProfileViewModel by viewModels {
            factory
        }

        dialogBuilder.setMessage("Anda yakin ingin log out?")
            .setCancelable(true)
            .setPositiveButton("LOG OUT") { _, _ ->
                viewModel.saveUserSession(false)}
            .setNegativeButton("KEMBALI") { _, _ ->
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Konfirmasi Log Out")
        alert.show()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}