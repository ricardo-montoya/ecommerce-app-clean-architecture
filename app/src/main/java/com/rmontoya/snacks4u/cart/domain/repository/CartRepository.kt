package com.rmontoya.snacks4u.cart.domain.repository

import android.content.Context
import com.rmontoya.snacks4u.cart.domain.model.CartProduct
import com.rmontoya.snacks4u.product.data.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository{
    suspend fun getFromFirestore() : Flow<List<CartProduct>>
    suspend fun writeToSharedPreferences(list : String)
    suspend fun getCartProducts() : List<CartProduct>
    suspend fun addProductToCart(product: CartProduct)
    suspend fun writeCartProducts(list : List<CartProduct>)
    suspend fun removeProduct(product: CartProduct)
}