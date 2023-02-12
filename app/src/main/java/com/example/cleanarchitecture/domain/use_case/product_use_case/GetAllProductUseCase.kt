package com.example.cleanarchitecture.domain.use_case.product_use_case

import com.example.cleanarchitecture.domain.model.Product
import com.example.cleanarchitecture.domain.repository.ProductRepository
import com.example.cleanarchitecture.domain.use_case.base.BaseUseCase
import com.example.cleanarchitecture.util.ResponseL
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetAllProductBaseUseCase = BaseUseCase<Unit, Flow<ResponseL<List<Product>>>>

class GetAllProductUseCase @Inject constructor(
    private val repository: ProductRepository
) : GetAllProductBaseUseCase {
    override suspend fun invoke(parameter: Unit): Flow<ResponseL<List<Product>>> {
        return repository.getAllProducts()
    }
}