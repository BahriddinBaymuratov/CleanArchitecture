package com.example.cleanarchitecture.presentation.auth

import android.os.Bundle
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.presentation.base.BaseActivity

class AuthActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()
    }
}