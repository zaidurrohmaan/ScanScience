package com.example.scanscience.ui.login

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
import com.example.scanscience.databinding.ActivityLoginBinding
import com.example.scanscience.ui.camera.CameraActivity
import com.example.scanscience.ui.home.HomeActivity
import com.example.scanscience.ui.signup.SignUpActivity
import com.example.scanscience.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@LoginActivity)
        val viewModel: LoginViewModel by viewModels {
            factory
        }

        buttonEffect(binding.btnLogin)

        binding.tvToDaftar.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            var valid = true
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(email.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.pastikan_semua_field_terisi))
            } else {
                viewModel.loginUser(email, password)
            }
        }

        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val data = result.data
                    val token = data?.token
                    if (token != null) {
                        viewModel.saveUserToken(token)
                        viewModel.saveUserSession(true)
                    }
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(getString(R.string.email_atau_password_salah))
                }

                else -> {
                    showLoading(false)
                    showToast(getString(R.string.terjadi_kesalahan))
                }
            }
        }

        viewModel.session.observe(this) {
            if (it) {
                startActivity(Intent(this@LoginActivity, CameraActivity::class.java))
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isClickable = false
            binding.tvToDaftar.isClickable = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isClickable = true
            binding.tvToDaftar.isClickable = true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
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