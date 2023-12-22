package com.example.scanscience.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.scanscience.R
import com.example.scanscience.ui.home.HomeActivity
import com.example.scanscience.ui.login.LoginActivity
import com.example.scanscience.ui.signup.SignUpActivity
import com.example.scanscience.utils.UserPreferences
import com.example.scanscience.utils.dataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val pref = UserPreferences.getInstance(application.dataStore)

        Handler(Looper.getMainLooper()).postDelayed({
            GlobalScope.launch {
                if (pref.getUserSession().first()) {
                    startActivity(Intent(this@SplashScreen, HomeActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashScreen, SignUpActivity::class.java))
                    finish()
                }
            }
        }, 2000)
    }
}