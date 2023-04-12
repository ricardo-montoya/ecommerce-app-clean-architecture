package com.rmontoya.snacks4u.product.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.rmontoya.snacks4u.product.data.Resource
import com.rmontoya.snacks4u.product.data.database.ProductDao
import com.rmontoya.snacks4u.product.data.database.ProductEntity
import com.rmontoya.snacks4u.product.data.database.asDomainModel
import com.rmontoya.snacks4u.product.data.remote.COLLECTION_PRODUCTS
import com.rmontoya.snacks4u.product.data.remote.FIRESTORE_ERROR_TAG
import com.rmontoya.snacks4u.product.domain.Product
import com.rmontoya.snacks4u.product.domain.asEntityModel
import com.rmontoya.snacks4u.product.domain.repository.ProductRespository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val firestore: FirebaseFirestore,
    val productDao: ProductDao
) : ProductRespository {
    override suspend fun fetchAllProducts(): Resource<List<Product>> {
        return try {
            val data = firestore.collection(COLLECTION_PRODUCTS).get().await()
                .toObjects(Product::class.java)
            Resource.Success(data)
        } catch (e: Exception) {
            Log.e(FIRESTORE_ERROR_TAG, e.localizedMessage ?: "Unknown Error")
            Resource.Failed(e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun getAllProducts(): List<Product> {
        return productDao.getAll().map { productEntity ->
            productEntity.asDomainModel()
        }
    }

    override suspend fun insertProductsToCache(products: List<Product>) {
        productDao.insertAll(
            *(products.map {
                it.asEntityModel()
            }.toTypedArray())
        )
    }

    override suspend fun clearCacheProducts(){
        productDao.clearAll()
    }

    override suspend fun getProductById(id: String): Flow<ProductEntity> {
        return productDao.getProductById(id)
    }
}