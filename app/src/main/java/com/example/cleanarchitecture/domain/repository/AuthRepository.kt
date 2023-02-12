package com.example.cleanarchitecture.domain.repository

import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.util.ResponseL
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(user:User): Flow<ResponseL<Boolean>>
    suspend fun register(user:User): Flow<ResponseL<Boolean>>
    fun logOut()
}