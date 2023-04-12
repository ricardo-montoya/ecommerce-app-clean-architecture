package com.rmontoya.snacks4u.product.domain.use_case

import com.rmontoya.snacks4u.product.data.Resource
import com.rmontoya.snacks4u.product.domain.Product
import com.rmontoya.snacks4u.product.domain.repository.ProductRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductsUseCase
@Inject
constructor(
    val productRepository: ProductRespository
) {
    suspend operator fun invoke(): Resource<List<Product>> {
        return withContext(Dispatchers.IO) {
            val networkCall = productRepository.fetchAllProducts()
            when (networkCall) {
                is Resource.Failed -> Resource.Failed(networkCall.message)
                Resource.Loading -> Resource.Loading
                is Resource.Success -> {
                    productRepository.clearCacheProducts()
                    productRepository.insertProductsToCache(networkCall.data)
                    val productList = productRepository.getAllProducts()
                    Resource.Success(productList.shuffled())
                }
            }
        }
    }
}