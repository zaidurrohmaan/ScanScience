package com.example.scanscience.ui.explore

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.scanscience.R
import com.example.scanscience.databinding.ActivityExploreBinding

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonEffect(btnFaktaSains)
            buttonEffect(btnExperiment)
            buttonEffect(btnUpdate)

            btnExperiment.setOnClickListener {
                showToast("Fitur dalam pengembangan")
            }

            btnUpdate.setOnClickListener {
                showToast("Fitur dalam pengembangan")
            }

            btnFaktaSains.setOnClickListener {
                showToast("Fitur dalam pengembangan")
            }
        }
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

    private fun showToast(message: String) {
        Toast.makeText(this@ExploreActivity, message, Toast.LENGTH_SHORT).show()
    }
}