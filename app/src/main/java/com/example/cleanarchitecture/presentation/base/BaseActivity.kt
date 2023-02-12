package com.example.cleanarchitecture.presentation.base

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    fun snackBar(v: View, text: String) {
        Snackbar.make(v, text, Snackbar.LENGTH_SHORT).show()
    }

    fun intent(to: Activity) {
        startActivity(Intent(this, to::class.java))
        finish()
    }
}