package com.example.scanscience.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scanscience.R
import com.example.scanscience.databinding.ActivitySignUpBinding
import com.example.scanscience.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(binding.root)

        binding.tvToMasuk.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }
    }
}