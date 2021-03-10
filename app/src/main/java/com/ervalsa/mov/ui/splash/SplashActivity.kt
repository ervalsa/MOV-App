package com.ervalsa.mov.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ervalsa.mov.R
import com.ervalsa.mov.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({
            val showOnboarding = Intent(this@SplashActivity, OnboardingActivity::class.java)
            startActivity(showOnboarding)
            finish()
        }, 2000)
    }
}