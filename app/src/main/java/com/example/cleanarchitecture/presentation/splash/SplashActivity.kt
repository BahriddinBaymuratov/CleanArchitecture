package com.example.cleanarchitecture.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cleanarchitecture.presentation.main.MainActivity
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.presentation.auth.AuthActivity
import com.example.cleanarchitecture.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.d("@Splash", "onCreate: ${viewModel.userId}")

        object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                if (viewModel.isUserNotNull()) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                    finish()
                }

            }
        }.start()
    }
}