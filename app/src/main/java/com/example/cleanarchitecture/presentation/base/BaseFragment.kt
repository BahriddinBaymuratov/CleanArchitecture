package com.example.cleanarchitecture.presentation.base

import android.content.Intent
import android.util.Patterns
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.cleanarchitecture.presentation.main.MainActivity
import com.example.cleanarchitecture.presentation.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment(
    @LayoutRes layout: Int
) : Fragment(layout) {

    fun Fragment.snackBar(text: String) {
        Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT).show()
    }

    fun View.click(action: (View) -> Unit) {
        this.setOnClickListener {
            action(it)
        }
    }

    fun validate(email: String, password: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6) {
            return true
        }
        return false
    }

    fun startActivity() {
        val ac = (activity as AuthActivity)
        ac.startActivity(Intent(ac, MainActivity::class.java))
        ac.finish()
    }

}