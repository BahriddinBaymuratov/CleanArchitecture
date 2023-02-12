package com.example.cleanarchitecture.domain.use_case.product_use_case

import com.example.cleanarchitecture.domain.model.Product
import com.example.cleanarchitecture.domain.repository.ProductRepository
import com.example.cleanarchitecture.util.ResponseL
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        oldProduct: Product,
        newProduct: Map<String, Any>
    ): ResponseL<Boolean> {
        return repository.updateProduct(oldProduct, newProduct)
    }

}