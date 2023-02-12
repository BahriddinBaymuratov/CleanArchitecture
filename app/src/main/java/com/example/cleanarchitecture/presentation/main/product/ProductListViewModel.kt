package com.example.cleanarchitecture.presentation.main.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.domain.use_case.base.AllUseCase
import com.example.cleanarchitecture.util.ResponseL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val useCase: AllUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ProductListState> = MutableStateFlow(ProductListState.Idle)
    val state: StateFlow<ProductListState> get() = _state

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            useCase.getAllProductUseCase(Unit).collect { response ->
                when (response) {
                    is ResponseL.Loading -> _state.update {
                        ProductListState.Loading
                    }
                    is ResponseL.Error -> _state.update {
                        ProductListState.Error(response.message)
                    }
                    is ResponseL.Success -> _state.update {
                        ProductListState.Success(response.data)
                    }
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            useCase.logOutUseCase.logOut()
            Log.d("@@@", "logOut: viewModel")
        }
    }

}