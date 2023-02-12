package com.example.cleanarchitecture.presentation.main.detail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.domain.model.Product
import com.example.cleanarchitecture.domain.use_case.base.AllUseCase
import com.example.cleanarchitecture.util.ResponseL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: AllUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<DetailState> = MutableStateFlow(DetailState.Idle)
    val state: StateFlow<DetailState> get() = _state

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            useCase.deleteProductUseCase(product).collect {
                when (it) {
                    is ResponseL.Loading -> _state.update {
                        DetailState.Loading
                    }
                    is ResponseL.Error -> Unit
                    is ResponseL.Success -> _state.update {
                        DetailState.Success
                    }
                }
            }
        }
    }
}