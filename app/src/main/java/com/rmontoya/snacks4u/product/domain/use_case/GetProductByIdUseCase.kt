package com.rmontoya.snacks4u.product.domain.use_case

import com.rmontoya.snacks4u.product.data.database.ProductEntity
import com.rmontoya.snacks4u.product.domain.repository.ProductRespository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase
@Inject
constructor(val productRepository: ProductRespository) {

    operator suspend fun invoke(id : String) : Flow<ProductEntity> {
        return productRepository.getProductById(id)
    }

}