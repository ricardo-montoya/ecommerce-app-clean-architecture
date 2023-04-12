package com.rmontoya.snacks4u.cart.domain

import com.rmontoya.snacks4u.cart.domain.model.CartProduct
import com.rmontoya.snacks4u.cart.domain.repository.CartRepository
import javax.inject.Inject

class RemoveCartProductUseCase @Inject constructor(
    val cartRepository: CartRepository
){
    suspend operator fun invoke(cartProduct : CartProduct){
        cartRepository.removeProduct(cartProduct)
    }
}