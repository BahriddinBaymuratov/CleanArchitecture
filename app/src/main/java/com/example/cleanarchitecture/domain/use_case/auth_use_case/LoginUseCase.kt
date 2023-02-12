package com.example.cleanarchitecture.domain.use_case.auth_use_case

import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.repository.AuthRepository
import com.example.cleanarchitecture.domain.use_case.base.BaseUseCase
import com.example.cleanarchitecture.util.ResponseL
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias LoginBaseUseCase = BaseUseCase<User, Flow<ResponseL<Boolean>>>

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) :LoginBaseUseCase{
    override suspend fun invoke(parameter: User): Flow<ResponseL<Boolean>> {
        return repository.login(parameter)
    }
}