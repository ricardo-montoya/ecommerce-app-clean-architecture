package com.rmontoya.snacks4u.cart.data.remote.repository

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rmontoya.snacks4u.R
import com.rmontoya.snacks4u.cart.domain.model.CartProduct
import com.rmontoya.snacks4u.cart.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val USERS_CARTS = "USERS_CARTS"
const val NO_EMAIL = "NO_EMAIL"
const val CART_PRODUCTS = "CART_PRODUCTS"

class CartRepositoryImpl
@Inject
constructor(
    val context: Context,
    val firestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth,
    val gson: Gson
) : CartRepository {
    override suspend fun getFromFirestore(): Flow<List<CartProduct>> = callbackFlow {
        val userEmail = firebaseAuth.currentUser?.email ?: NO_EMAIL
        val eventDocument = firestore
            .collection(USERS_CARTS)
            .document(userEmail)
        var listItems: List<CartProduct> = emptyList()
        val subscription = eventDocument.addSnapshotListener { snapshot, _ ->
            if (snapshot!!.exists()) {
                val lisItemJson = snapshot.getString(CART_PRODUCTS)
                lisItemJson?.let {
                    val typeToken = object : TypeToken<ArrayList<CartProduct>>() {}.type
                    val listItem = gson.fromJson<ArrayList<CartProduct>>(
                        lisItemJson,
                        typeToken
                    ) as List<CartProduct>
                    listItems = listItem
                    trySend(listItem)
                }
            }
        }
        if (listItems.isNotEmpty()) {
            writeCartProducts(listItems)
        }
        trySend(getCartProducts())
        awaitClose { subscription.remove() }
    }

    override suspend fun writeToSharedPreferences(list: String) {
        val email = firebaseAuth.currentUser?.email ?: NO_EMAIL
        val sharedPreferences = context.getSharedPreferences(email, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(CART_PRODUCTS, list).apply()
    }


    override suspend fun writeCartProducts(list: List<CartProduct>) {
        val listAsJson = gson.toJson(list as ArrayList)
        val email = firebaseAuth.currentUser?.email ?: NO_EMAIL
        writeToSharedPreferences(listAsJson)
        val eventDocument = firestore.collection(USERS_CARTS).document(email)
        val data = hashMapOf(
            CART_PRODUCTS to listAsJson
        )
        eventDocument.set(data).addOnSuccessListener {
            Log.i(CART_PRODUCTS, "Uploading Success")
        }.addOnFailureListener {
            Log.e(CART_PRODUCTS, it.localizedMessage ?: "Unexpected error")
        }
    }

    override suspend fun getCartProducts(): List<CartProduct> {
        val email = firebaseAuth.currentUser?.email ?: NO_EMAIL
        val sharedPreferences = context.getSharedPreferences(email, Context.MODE_PRIVATE)
        val listAsJson = sharedPreferences.getString(CART_PRODUCTS, null)
        var list: List<CartProduct> = emptyList()
        val typeToken = object : TypeToken<ArrayList<CartProduct>>() {}.type
        listAsJson?.let {
            list = gson.fromJson(listAsJson, typeToken) as List<CartProduct>
        }
        return list
    }

    override suspend fun addProductToCart(product: CartProduct) {
        val products = getCartProducts() + product
        writeCartProducts(products)
    }

    override suspend fun removeProduct(product: CartProduct) {
        val products = getCartProducts().filter {
            it != product
        }
        writeCartProducts(products)
    }
}