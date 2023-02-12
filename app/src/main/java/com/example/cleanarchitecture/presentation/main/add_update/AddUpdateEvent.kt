package com.example.cleanarchitecture.presentation.main.add_update

import com.example.cleanarchitecture.domain.model.Product

sealed class AddUpdateEvent {
    data class OnCreateProduct(val product: Product) : AddUpdateEvent()
    data class OnUpdateProduct(
        val oldProduct: Product,
        val newProduct: Map<String, Any>
        ) : AddUpdateEvent()
}