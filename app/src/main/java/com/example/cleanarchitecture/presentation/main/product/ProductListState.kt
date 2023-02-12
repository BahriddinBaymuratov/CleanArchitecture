package com.example.cleanarchitecture.presentation.main.product

import com.example.cleanarchitecture.domain.model.Product

sealed class ProductListState {
    object Idle : ProductListState()
    object Loading : ProductListState()
    data class Error(val text: String) : ProductListState()
    data class Success(val productList: List<Product>) : ProductListState()
}