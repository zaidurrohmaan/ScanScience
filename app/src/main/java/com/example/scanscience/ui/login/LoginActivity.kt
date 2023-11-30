package com.example.scanscience.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scanscience.R
import com.example.scanscience.databinding.ActivityLoginBinding
import com.example.scanscience.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(binding.root)

        binding.tvToMasuk.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

    }
}