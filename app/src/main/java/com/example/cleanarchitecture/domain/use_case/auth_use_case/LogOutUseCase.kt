package com.example.cleanarchitecture.domain.use_case.auth_use_case

import android.util.Log
import com.example.cleanarchitecture.domain.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    fun logOut(){
        repository.logOut()
        Log.d("@@@", "logOut: Log out usecase working")
    }
}