package com.example.cleanarchitecture.data.di

import com.example.cleanarchitecture.data.repository.AuthRepositoryImpl
import com.example.cleanarchitecture.data.repository.ProductRepositoryImpl
import com.example.cleanarchitecture.domain.repository.AuthRepository
import com.example.cleanarchitecture.domain.repository.ProductRepository
import com.example.cleanarchitecture.domain.use_case.auth_use_case.LogOutUseCase
import com.example.cleanarchitecture.domain.use_case.auth_use_case.LoginUseCase
import com.example.cleanarchitecture.domain.use_case.auth_use_case.RegisterUseCase
import com.example.cleanarchitecture.domain.use_case.base.AllUseCase
import com.example.cleanarchitecture.domain.use_case.product_use_case.CreateProductUseCase
import com.example.cleanarchitecture.domain.use_case.product_use_case.DeleteProductUseCase
import com.example.cleanarchitecture.domain.use_case.product_use_case.GetAllProductUseCase
import com.example.cleanarchitecture.domain.use_case.product_use_case.UpdateProductUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(auth, fireStore)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        fireStore: FirebaseFirestore,
        auth: FirebaseAuth
    ): ProductRepository {
        return ProductRepositoryImpl(fireStore, auth)
    }

    @Provides
    @Singleton
    fun provideAllUseCases(
        authRepository: AuthRepository,
        productRepository: ProductRepository
    ): AllUseCase {
        return AllUseCase(
            loginUseCase = LoginUseCase(authRepository),
            registerUseCase = RegisterUseCase(authRepository),
            logOutUseCase = LogOutUseCase(authRepository),
            createProductUseCase = CreateProductUseCase(productRepository),
            updateProductUseCase = UpdateProductUseCase(productRepository),
            getAllProductUseCase = GetAllProductUseCase(productRepository),
            deleteProductUseCase = DeleteProductUseCase(productRepository)
        )
    }

}