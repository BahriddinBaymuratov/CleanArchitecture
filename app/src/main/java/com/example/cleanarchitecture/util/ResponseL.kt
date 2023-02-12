package com.example.cleanarchitecture.util

sealed class ResponseL<out T> {
    object Loading : ResponseL<Nothing>()
    data class Error(val message: String) : ResponseL<Nothing>()
    data class Success<out T>(val data: T) : ResponseL<T>()
}