package com.example.cleanarchitecture.data.repository

import com.example.cleanarchitecture.domain.model.Product
import com.example.cleanarchitecture.domain.repository.ProductRepository
import com.example.cleanarchitecture.util.ResponseL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    auth: FirebaseAuth
) : ProductRepository {
    private var isSuccessful = false
    private val uid = auth.currentUser?.uid

    override suspend fun getAllProducts(): Flow<ResponseL<List<Product>>> = callbackFlow{
        ResponseL.Loading
        val snap = fireStore.collection("products")
            .whereEqualTo("userId", uid)
            .addSnapshotListener { value, error ->
                val response = if (value != null){
                    val productList = value.toObjects(Product::class.java)
                    ResponseL.Success(productList)
                }else {
                    ResponseL.Error(error?.message.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snap.remove()
        }
    }

    override suspend fun createProduct(product: Product): Flow<ResponseL<Boolean>> = flow {
        isSuccessful = false
        emit(ResponseL.Loading)
        try {
            val productId = fireStore.collection("product").document().id
            val newProduct = Product(userId = "$uid", name = product.name, price = product.price)
            fireStore.collection("products").document(productId).set(newProduct)
                .addOnSuccessListener {
                    isSuccessful = true
                }.await()
            emit(ResponseL.Success(isSuccessful))

        } catch (e: Exception) {
            emit(ResponseL.Error(e.message.toString()))
        }

    }

    override suspend fun updateProduct(
        oldProduct: Product,
        newProduct: Map<String, Any>
    ): ResponseL<Boolean> {
        isSuccessful = false
        return try {
            val query = fireStore.collection("products")
                .whereEqualTo("name", oldProduct.name)
                .whereEqualTo("price", oldProduct.price)
                .whereEqualTo("userId", oldProduct.userId)
                .get()
                .await()
            if (query.documents.isNotEmpty()) {
                for (doc in query) {
                    fireStore.collection("products")
                        .document(doc.id)
                        .set(
                            newProduct,
                            SetOptions.merge()
                        ).addOnSuccessListener {
                            isSuccessful = true
                        }.await()
                }
            }
            ResponseL.Success(isSuccessful)
        } catch (e: Exception) {
            ResponseL.Error(e.message.toString())
        }

    }

    override suspend fun deleteProduct(product: Product): Flow<ResponseL<Boolean>> = flow {
        isSuccessful = false
        emit(ResponseL.Loading)
        try {
            val query = fireStore.collection("products")
                .whereEqualTo("name", product.name)
                .whereEqualTo("price", product.price)
                .whereEqualTo("userId", product.userId)
                .get()
                .await()
            if (query.documents.isNotEmpty()) {
                for (doc in query) {
                    fireStore.collection("products").document(doc.id)
                        .delete().addOnSuccessListener {
                            isSuccessful = true
                        }.await()
                }
                emit(ResponseL.Success(isSuccessful))
            }

        } catch (e: Exception) {
            emit(ResponseL.Error(e.message.toString()))
        }

    }



}