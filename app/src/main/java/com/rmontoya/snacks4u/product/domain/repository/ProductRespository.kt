package com.rmontoya.snacks4u.product.domain.repository

import com.rmontoya.snacks4u.product.data.Resource
import com.rmontoya.snacks4u.product.data.database.ProductEntity
import com.rmontoya.snacks4u.product.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRespository {
    suspend fun fetchAllProducts() : Resource<List<Product>>

    suspend fun getAllProducts() : List<Product>

    suspend fun insertProductsToCache(products : List<Product>)

    suspend fun clearCacheProducts()
    suspend fun getProductById(id: String): Flow<ProductEntity>
}