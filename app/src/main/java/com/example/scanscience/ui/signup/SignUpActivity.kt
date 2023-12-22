package com.example.scanscience.ui.signup

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.scanscience.R
import com.example.scanscience.data.Result
import com.example.scanscience.databinding.ActivitySignUpBinding
import com.example.scanscience.ui.login.LoginActivity
import com.example.scanscience.utils.ViewModelFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: SignUpViewModel by viewModels {
            factory
        }

        buttonEffect(binding.btnSignup)

        binding.tvToMasuk.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }

        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty()) {
                showToast(getString(R.string.pastikan_semua_field_terisi))
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Email tidak sesuai format")
            }
            else {
                viewModel.registerUser(name, email, password)
            }
        }

        viewModel.registerResult.observe(this) { result ->
            when(result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    showToast(getString(R.string.registrasi_berhasil))
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(getString(R.string.email_tidak_tersedia_silakan_gunakan_email_lain))
                }

                else -> {
                    showLoading(false)
                    showToast(getString(R.string.terjadi_kesalahan))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnSignup.isClickable = false
            binding.tvToMasuk.isClickable = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnSignup.isClickable = true
            binding.tvToMasuk.isClickable = true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
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