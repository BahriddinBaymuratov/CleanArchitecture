package com.example.cleanarchitecture.domain.repository

import com.example.cleanarchitecture.domain.model.Product
import com.example.cleanarchitecture.util.ResponseL
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun createProduct(product: Product): Flow<ResponseL<Boolean>>
    suspend fun updateProduct(oldProduct: Product, newProduct: Map<String, Any>): ResponseL<Boolean>
    suspend fun deleteProduct(product: Product): Flow<ResponseL<Boolean>>
    suspend fun getAllProducts(): Flow<ResponseL<List<Product>>>
}