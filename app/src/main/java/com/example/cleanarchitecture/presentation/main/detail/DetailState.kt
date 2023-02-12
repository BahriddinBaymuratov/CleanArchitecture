package com.example.cleanarchitecture.presentation.main.detail

sealed class DetailState {
    object Idle : DetailState()
    object Loading : DetailState()
    object Success : DetailState()
}