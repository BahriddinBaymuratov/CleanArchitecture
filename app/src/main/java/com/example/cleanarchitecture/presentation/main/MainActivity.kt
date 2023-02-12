package com.example.cleanarchitecture.presentation.main

import android.os.Bundle
import com.example.cleanarchitecture.R
import com.example.cleanarchitecture.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}