package com.example.cleanarchitecture.data.repository

import android.util.Log
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.repository.AuthRepository
import com.example.cleanarchitecture.util.ResponseL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : AuthRepository {
    private var isSuccessful = false
    override suspend fun login(user: User): Flow<ResponseL<Boolean>> = flow {
        isSuccessful = false
        try {
            emit(ResponseL.Loading)
            auth.signInWithEmailAndPassword(user.email, user.password).addOnSuccessListener {
                isSuccessful = true
            }.await()
            emit(ResponseL.Success(isSuccessful))
        } catch (e: Exception) {
            emit(ResponseL.Error(e.message.toString()))
        }
    }

    override suspend fun register(user: User): Flow<ResponseL<Boolean>> = flow {
        isSuccessful = false
        try {
            emit(ResponseL.Loading)
            auth.createUserWithEmailAndPassword(user.email, user.password).addOnSuccessListener {
                val id = auth.currentUser?.uid!!
                fireStore.collection("users").document(id).set(user)
                    .addOnSuccessListener {
                        isSuccessful = true
                    }
            }.await()
            emit(ResponseL.Success(isSuccessful))
        } catch (e: Exception) {
            emit(ResponseL.Error(e.message.toString()))
        }
    }

    override fun logOut() {
        auth.signOut()
        Log.d("@@@ Impl", "logOut: working")
    }
}