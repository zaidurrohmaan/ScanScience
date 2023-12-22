package com.example.scanscience.ui.camera

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.scanscience.R
import com.example.scanscience.databinding.ActivityCameraBinding
import com.example.scanscience.ui.result.ResultActivity
import com.example.scanscience.ui.profile.ProfileActivity
import com.example.scanscience.ui.home.HomeActivity
import com.example.scanscience.utils.getImageUri

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonEffect(btnOpenGallery)
            buttonEffect(btnOpenCamera)
            buttonEffect(btnScan)
        }

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this@CameraActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this@CameraActivity, ProfileActivity::class.java))
            finish()
        }

        binding.btnScan.setOnClickListener{
           scanImage()
        }

        binding.btnOpenGallery.setOnClickListener {
            startGallery()
        }

        binding.btnOpenCamera.setOnClickListener {
            startCamera()
        }
    }


    var doubleBackToExitOnce:Boolean = false

    override fun onBackPressed() {
        if(doubleBackToExitOnce){
            super.onBackPressed()
            return
        }

        this.doubleBackToExitOnce = true

        //displays a toast message when user clicks exit button
        Toast.makeText(this@CameraActivity,
            getString(R.string.tekan_lagi_untuk_keluar_aplikasi), Toast.LENGTH_SHORT).show()

        //displays the toast message for a while
        Handler().postDelayed({
            kotlin.run { doubleBackToExitOnce = false }
        }, 2000)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPicture.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun scanImage() {
        currentImageUri?.let { uri ->
            val intent = Intent(this@CameraActivity, ResultActivity::class.java)
            intent.putExtra(ResultActivity.EXTRA_URI, uri.toString())
            startActivity(intent)
        } ?: showToast("Pilih gambar terlebih dahulu")
    }

    private fun showToast(message: String) {
        Toast.makeText(this@CameraActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun buttonEffect(button: View) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
    }
}