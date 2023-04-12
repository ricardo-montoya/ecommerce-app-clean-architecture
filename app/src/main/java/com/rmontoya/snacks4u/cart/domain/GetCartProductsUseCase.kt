package com.rmontoya.snacks4u.cart.domain

import com.rmontoya.snacks4u.cart.domain.model.CartProduct
import com.rmontoya.snacks4u.cart.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    val cartRepository: CartRepository
) {
    suspend operator fun invoke(): Flow<List<CartProduct>> {
        return cartRepository.getFromFirestore()
    }
}