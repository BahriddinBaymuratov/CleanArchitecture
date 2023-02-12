package com.example.cleanarchitecture.domain.use_case.product_use_case

import com.example.cleanarchitecture.domain.model.Product
import com.example.cleanarchitecture.domain.repository.ProductRepository
import com.example.cleanarchitecture.domain.use_case.base.BaseUseCase
import com.example.cleanarchitecture.util.ResponseL
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias DeleteProductBaseUseCase = BaseUseCase<Product, Flow<ResponseL<Boolean>>>

class DeleteProductUseCase @Inject constructor(
    private val repository: ProductRepository
) : DeleteProductBaseUseCase {
    override suspend fun invoke(parameter: Product): Flow<ResponseL<Boolean>> {
        return repository.deleteProduct(parameter)
    }
}