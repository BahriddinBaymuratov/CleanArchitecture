package com.example.cleanarchitecture.presentation.main.add_update

sealed class AddUpdateState {
    object Idle : AddUpdateState()
    object Loading : AddUpdateState()
    data class Error(val message: String) : AddUpdateState()
    object SuccessCreate : AddUpdateState()
    object SuccessUpdate : AddUpdateState()
}