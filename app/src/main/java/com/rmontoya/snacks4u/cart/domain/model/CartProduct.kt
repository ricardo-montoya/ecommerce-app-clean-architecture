package com.rmontoya.snacks4u.cart.domain.model

import com.rmontoya.snacks4u.product.domain.Product

data class CartProduct(
    val product : Product,
    val amount : Int
)
