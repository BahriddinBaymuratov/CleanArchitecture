package com.example.cleanarchitecture.domain.use_case.auth_use_case

import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.repository.AuthRepository
import com.example.cleanarchitecture.domain.use_case.base.BaseUseCase
import com.example.cleanarchitecture.util.ResponseL
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias RegisterBaseUseCase = BaseUseCase<User, Flow<ResponseL<Boolean>>>

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) : RegisterBaseUseCase {
    override suspend fun invoke(parameter: User): Flow<ResponseL<Boolean>> {
        return repository.register(parameter)
    }
}