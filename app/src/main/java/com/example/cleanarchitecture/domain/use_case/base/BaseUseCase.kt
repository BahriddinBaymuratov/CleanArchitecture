package com.example.cleanarchitecture.domain.use_case.base

interface BaseUseCase<in parameter, out Result> {
    suspend operator fun invoke(parameter: parameter): Result
}