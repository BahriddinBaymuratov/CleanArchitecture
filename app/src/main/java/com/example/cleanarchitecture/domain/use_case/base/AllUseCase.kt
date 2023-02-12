package com.example.cleanarchitecture.domain.use_case.base

import com.example.cleanarchitecture.domain.use_case.auth_use_case.LogOutUseCase
import com.example.cleanarchitecture.domain.use_case.auth_use_case.LoginUseCase
import com.example.cleanarchitecture.domain.use_case.auth_use_case.RegisterUseCase
import com.example.cleanarchitecture.domain.use_case.product_use_case.CreateProductUseCase
import com.example.cleanarchitecture.domain.use_case.product_use_case.DeleteProductUseCase
import com.example.cleanarchitecture.domain.use_case.product_use_case.GetAllProductUseCase
import com.example.cleanarchitecture.domain.use_case.product_use_case.UpdateProductUseCase

data class AllUseCase(
    val registerUseCase: RegisterUseCase,
    val loginUseCase: LoginUseCase,
    val logOutUseCase: LogOutUseCase,
    val createProductUseCase: CreateProductUseCase,
    val getAllProductUseCase: GetAllProductUseCase,
    val updateProductUseCase: UpdateProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase
)